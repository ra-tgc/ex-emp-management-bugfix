package jp.co.sample.emp_management.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	private final String FILEPATH = "C:/env/spring-workspace/ex-emp-management-bugfix/src/main/resources/static/img/";

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertEmployeeForm setUpInsertEmployeeForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.<br>
	 * 名前の検索フォームが利用された場合には従業員名の曖昧検索を行います。
	 * 検索結果が一件も存在しなかった場合は「１件もありませんでした」というメッセージの表示と全件検索の結果を出力します。
	 * 
	 * @param name  検索する名前
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(String name, Model model) {

		List<Employee> employeeList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();

		if (name == null || name.isBlank()) {
			// 名前検索フォームが利用されていない or 名前検索フォームが空欄だった場合
			employeeList = employeeService.showList();
		} else {
			employeeList = employeeService.searchByName(name);
		}

		// 検索結果が一件も存在しなかった場合
		if (employeeList.isEmpty()) {
			employeeList = employeeService.showList();
			model.addAttribute("isEmptyEmployeeList", "１件もありませんでした");
		}

		List<Employee> allEmployeeList = employeeService.showList();
		for (Employee employee : allEmployeeList) {
			nameList.add(employee.getName());
		}

		model.addAttribute("employeeList", employeeList);
		model.addAttribute("nameList", nameList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 * 従業員情報を登録します.
	 * 
	 * @param form   従業員情報用フォーム
	 * @param result エラー情報格納用オブジェクト
	 * @param model  リクエストスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			String fileName = form.getImage();
			Path filePath = Paths.get(FILEPATH + fileName);
			if (Files.exists(filePath)) {
				try {
					Files.delete(filePath);
					FieldError fieldError = new FieldError(result.getObjectName(), "image", "もう一度アップロードし直してください");
					result.addError(fieldError);
					form.setImage(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return toInsert();
		}

		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		employee.setHireDate(java.sql.Date.valueOf(form.getHireDate()));
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));

		employeeService.insert(employee);

		return "redirect:/employee/showList";
	}

	/**
	 * 従業員登録画面に遷移します.
	 * 
	 * @return 従業員登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "employee/insert";
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String, String> upload(@RequestParam("file") MultipartFile image) {
		String fileName = image.getOriginalFilename();
		Path filePath = Paths.get(FILEPATH + fileName);
		Map<String, String> map = new HashMap<>();
		String uploadFileMessage = "";

		if (image.isEmpty()) {
			uploadFileMessage = "ファイルがアップロードされていません";
			map.put("uploadFileMessage", uploadFileMessage);
			return map;
		}

		if (!"image/jpeg".equals(image.getContentType()) && !"image/png".equals(image.getContentType())) {
			uploadFileMessage = "ファイルの形式がサポートされていません";
			map.put("uploadFileMessage", uploadFileMessage);
			return map;
		}

		try {
			// アップロードファイルをバイト値に変換
			byte[] bytes = image.getBytes();
			// バイト値を書き込むためのファイルを作成して指定したパスに格納
			OutputStream stream = Files.newOutputStream(filePath);
			// ファイルに書き込み
			stream.write(bytes);
			uploadFileMessage = "ファイルのアップロードに成功しました";
		} catch (Exception e) {
			e.printStackTrace();
			uploadFileMessage = "ファイルのアップロードに失敗しました";
		}
		map.put("uploadFileName", fileName);
		map.put("uploadFileMessage", uploadFileMessage);
		return map;
	}
}

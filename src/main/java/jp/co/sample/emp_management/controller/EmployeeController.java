package jp.co.sample.emp_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
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

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
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
}

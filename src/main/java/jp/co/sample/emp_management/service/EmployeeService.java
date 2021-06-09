package jp.co.sample.emp_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	/**
	 * <<<<<<< HEAD 従業員情報を登録します.
	 * 
	 * @param employee 登録する従業員情報
	 */
	public void insert(Employee employee) {
		employeeRepository.insert(employee);
	}

	/**
	 * ======= >>>>>>> develop 従業員情報を曖昧検索します.
	 * 
	 * @param name 名前
	 * @return 曖昧検索で一致する従業員情報一覧 存在しない場合は空のリストlが返ります
	 */
	public List<Employee> searchByName(String name) {
		List<Employee> employeeList = employeeRepository.findByName(name);
		return employeeList;
	}

	/**
	 * メールアドレスの重複がないか確認します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 従業員情報 重複しない場合はnullが返ります
	 */
	public Employee dupCheckEmail(String mailAddress) {
		Employee employee = employeeRepository.findByMailAddress(mailAddress);
		return employee;
	}
}

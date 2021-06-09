package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 従業員情報登録時に使用するフォーム.
 * 
 * @author masaki.taguchi
 *
 */
public class InsertEmployeeForm {
	/** 従業員名 */
	@Size(min = 1, max = 64, message = "1文字以上64文字以下で入力してください")
	private String name;
	/** 画像 */
	@Size(min = 1, max = 64, message = "ファイル名は1文字以上64文字以下にしてください")
	private String image;
	/** 性別 */
	@Size(min = 1, max = 64, message = "1文字以上64文字以下で入力してください")
	private String gender;
	/** 入社日 */
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "yyyy-MM-ddの形式で入力してください")
	private String hireDate;
	/** メールアドレス */
	@Email(message = "メールアドレスの形式にしてください")
	@Size(min = 1, max = 64, message = "1文字以上64文字以下で入力してください")
	private String mailAddress;
	/** 郵便番号 */
	@Pattern(regexp = "^\\d{3}-\\d{4}$", message = "XXX-XXXXの形式で入力してください")
	private String zipCode;
	/** 住所 */
	@Size(min = 1, max = 64, message = "1文字以上64文字以下で入力してください")
	private String address;
	/** 電話番号 */
	@Pattern(regexp = "^0\\d{1,4}-\\d{1,4}-\\d{4}$", message = "XXX-XXXX-XXXXかXX-XXXX-XXXXの形式で入力してください")
	private String telephone;
	/** 給料 */
	@Pattern(regexp = "^\\d{1,64}$", message = "1桁以上64桁以下で入力してください")
	private String salary;
	/** 特性 */
	@Size(min = 1, max = 64, message = "1文字以上64文字以下で入力してください")
	private String characteristics;
	/** 扶養人数 */
	@Pattern(regexp = "^\\d{1,2}$", message = "1桁以上2桁以下で入力してください")
	private String dependentsCount;

	public InsertEmployeeForm() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "InsertEmployeeForm [name=" + name + ", image=" + image + ", gender=" + gender + ", hireDate=" + hireDate
				+ ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address=" + address + ", telephone="
				+ telephone + ", salary=" + salary + ", characteristics=" + characteristics + ", dependentsCount="
				+ dependentsCount + "]";
	}

}

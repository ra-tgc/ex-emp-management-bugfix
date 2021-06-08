package jp.co.sample.emp_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 使用するパスワードエンコーダオブジェクトをインスタンス化する.
	 * 
	 * @return パスワードエンコーダ
	 */
	@Bean
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		String encodedPassword = passwordEncoder.encode(administrator.getPassword());
		administrator.setPassword(encodedPassword);

		administratorRepository.insert(administrator);
	}

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String password) {
		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);

		// ハッシュ化されたパスワードと照合し、一致しなければnullを返す
		if (!passwordEncoder.matches(password, administrator.getPassword())) {
			return null;
		}

		return administrator;
	}

	/**
	 * メールアドレスの重複がないか確認します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 管理者情報 重複しない場合はnullが返ります
	 */
	public Administrator dupCheckEmail(String mailAddress) {
		Administrator administartor = administratorRepository.findByMailAddress(mailAddress);
		return administartor;
	}
}


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBConnection_JavaEE05
 */
@WebServlet("/DBConnection_JavaEE05")
public class DBConnection_JavaEE05 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBConnection_JavaEE05() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");

		//インスタンス生成
		ProductDao productDao = new ProductDao();

		//ボタン判定値の取得
		String btn = request.getParameter("btn");

		// 入力情報を取得
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String price = request.getParameter("price");

		//宣言
		Integer idnum;
		String productname = name;
		Integer charge;

		//検索処理
		try {
			if (btn.equals("select")) {
				//Integerに変換可能かチェック
				if (DbUtil.isNullOrEmpty(id) == false) {
					if (DbUtil.isNumber(id) == false) {
						request.setAttribute("error1", "*数値を入力してください");
						request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
					}
				}

				if (DbUtil.isNullOrEmpty(price) == false) {
					if (DbUtil.isNumber(price) == false) {
						request.setAttribute("error2", "*please insert number");
						request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
					}
				}

				//Product product = new Product(request.getParameter("id"),request.getParameter("name"),request.getParameter("price"));
				//空文字チェック
				if (DbUtil.isNullOrEmpty(id)) {
					idnum = 0;
				} else {
					idnum = Integer.parseInt(id);
				}
				if (DbUtil.isNullOrEmpty(name)) {
					productname = null;
				}
				if (DbUtil.isNullOrEmpty(price)) {
					charge = 0;
				} else {
					charge = Integer.parseInt(price);
				}

				Product product = new Product(idnum, productname, charge);
				// 検索

				List<Product> list = productDao.find(product);

				// 結果をセッションに設定
				request.setAttribute("product", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//登録もしくは更新処理

		try {
			if (btn.equals("registerOrUpdate")) {

				if (DbUtil.isNullOrEmpty(id) == false) {
					if (DbUtil.isNumber(id) == false) {
						request.setAttribute("error1", "*数値を入力してください");
						request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
					}
				}

				if (DbUtil.isNullOrEmpty(id)) {
					idnum = 0;
				} else {
					idnum = Integer.parseInt(id);
				}

				if (productDao.findByIdExist(idnum)) {

					//更新処理

					//Integerに変換可能かチェック
					if (DbUtil.isNullOrEmpty(id) == false) {
						if (DbUtil.isNumber(id) == false) {
							request.setAttribute("error1", "*数値を入力してください");
							request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
						}
					}
					if (DbUtil.isNullOrEmpty(price) == false) {
						if (DbUtil.isNumber(price) == false) {
							request.setAttribute("error2", "*please insert number");
							request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
						}
					}

					//空文字チェック
					if (DbUtil.isNullOrEmpty(id)) {
						idnum = 0;
					} else {
						idnum = Integer.parseInt(id);
					}
					if (DbUtil.isNullOrEmpty(name)) {
						productname = null;
					}
					if (DbUtil.isNullOrEmpty(price)) {
						charge = 0;
					} else {
						charge = Integer.parseInt(price);
					}

					Product product = new Product(productname, charge, idnum);
					productDao.update(product);

				} else {

					//登録処理

					//Integerに変換可能かチェック
					if (DbUtil.isNullOrEmpty(price) == false) {
						if (DbUtil.isNumber(price) == false) {
							request.setAttribute("error2", "*please insert number");
							request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
						}
					}

					//空文字チェック
					if (DbUtil.isNullOrEmpty(name)) {
						productname = null;
					}
					if (DbUtil.isNullOrEmpty(price)) {
						charge = 0;
					} else {
						charge = Integer.parseInt(price);
					}

					Product product = new Product(productname, charge);
					productDao.register(product);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//削除処理
		try {
			if (btn.equals("delete")) {
				if (DbUtil.isNullOrEmpty(id) == false) {
					if (DbUtil.isNumber(id) == false) {
						request.setAttribute("error1", "*数値を入力してください");
						request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
					}
				}

				if (DbUtil.isNullOrEmpty(id)) {
					idnum = 0;
				} else {
					idnum = Integer.parseInt(id);
				}

				if (productDao.findByIdExist(idnum)) {

					//Integerに変換可能かチェック
					if (DbUtil.isNullOrEmpty(id) == false) {
						if (DbUtil.isNumber(id) == false) {
							request.setAttribute("error1", "*数値を入力してください");
							request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
						}
					}

					//空文字チェック
					if (DbUtil.isNullOrEmpty(id)) {
						idnum = 0;
					} else {
						idnum = Integer.parseInt(id);
					}

					Product product = new Product(idnum);
					productDao.delete(product);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 次画面指定
		request.getRequestDispatcher("dbconnection_javaee05.jsp").forward(request, response);
	}
}

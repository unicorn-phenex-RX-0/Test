
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBConnection_JavaEE01
 */
@WebServlet("/DBConnection_JavaEE01")
public class DBConnection_JavaEE01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBConnection_JavaEE01() {
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
		//doGet(request, response);
		// 文字化け対策
		request.setCharacterEncoding("UTF-8");

		// 入力情報を取得
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		if (DbUtil.isNullOrEmpty(id) == false) {
			if (DbUtil.isNumber(id) == false) {
				request.setAttribute("error1", "*数値を入力してください");
				request.getRequestDispatcher("dbconnection_javaee01.jsp").forward(request, response);
			}
		}

		if (DbUtil.isNullOrEmpty(price) == false) {
			if (DbUtil.isNumber(price) == false) {
				request.setAttribute("error2", "*please insert number");
				request.getRequestDispatcher("dbconnection_javaee01.jsp").forward(request, response);
			}
		}

		//Product product = new Product(request.getParameter("id"),request.getParameter("name"),request.getParameter("price"));
		Integer idnum;
		String productname = name;
		Integer charge;
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
		ProductDao productDao = new ProductDao();
		List<Product> list = productDao.find(product);

		// userを設定
		request.setAttribute("product", list);

		// 次画面指定
		request.getRequestDispatcher("dbconnection_javaee01.jsp").forward(request, response);
	}

}

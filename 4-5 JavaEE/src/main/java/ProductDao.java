
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

	private Connection connection = DbUtil.getConnection();

	public ProductDao(Connection con) {
		this.connection = connection;
	}

	public ProductDao() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private static final String SQL_INSERT = "INSERT INTO products(id,name,price) SELECT MAX(id)+1,?,? from products";

	public void register(Product product) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_INSERT)) {
			//stmt.setInt(1, product.getId());
			stmt.setString(1, product.getName());
			stmt.setInt(2, product.getPrice());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static final String SQL_SELECT_WHERE_ID = "SELECT id,name,price FROM products WHERE id = ?";

	public Product findById(int id) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_WHERE_ID)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public boolean findByIdExist(int id) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_WHERE_ID)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	private static final String SQL_UPDATE = "UPDATE products SET name = ?,price = ? WHERE id = ?";

	public void update(Product product) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE)) {

			stmt.setString(1, product.getName());
			stmt.setInt(2, product.getPrice());
			stmt.setInt(3, product.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	private static final String SQL_DELETE = "DELETE FROM products WHERE id = ?";

	public void delete(Product product) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE)) {
			stmt.setInt(1, product.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Product> find(Product product) {
		List<Product> list = new ArrayList<Product>();

		try {
			String sqladd = "SELECT * FROM products WHERE 1=1 ";
			PreparedStatement stmt = connection.prepareStatement(sqladd);

			if (product.getId() != 0) {

				sqladd += "AND id = ? ";
				stmt = connection.prepareStatement(sqladd);

				stmt.setInt(1, product.getId());

			}

			if (product.getName() != null) {
				sqladd += "AND name = ? ";
				stmt = connection.prepareStatement(sqladd);
				if (product.getId() != 0) {

					stmt.setInt(1, product.getId());
					stmt.setString(2, product.getName());

				} else {

					stmt.setString(1, product.getName());

				}
			}

			if (product.getPrice() != 0) {

				sqladd += "AND price = ?";
				stmt = connection.prepareStatement(sqladd);

				if (product.getId() != 0 && product.getName() != null) {

					stmt.setInt(1, product.getId());
					stmt.setString(2, product.getName());
					stmt.setInt(3, product.getPrice());

				} else if (product.getId() == 0 && product.getName() == null) {

					stmt.setInt(1, product.getPrice());

				} else if (product.getId() == 0 && product.getName() != null) {

					stmt.setString(1, product.getName());
					stmt.setInt(2, product.getPrice());

				} else if (product.getId() != 0 && product.getName() == null) {

					stmt.setInt(1, product.getId());
					stmt.setInt(2, product.getPrice());

				}
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Product p = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
				list.add(p);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}

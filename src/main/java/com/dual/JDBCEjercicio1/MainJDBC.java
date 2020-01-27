package com.dual.JDBCEjercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class MainJDBC {

	private static Connection connection;
	private static String cocheSeleccionado;

	public static void conectarBD() {

		try {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception ex) {
				System.out.println("Error al registrar el driver de MySQL: " + ex);
			}

			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coches", "root", "");

			boolean valid = connection.isValid(50000);
			if (valid) {
				JOptionPane.showMessageDialog(null, "Conexión a BD establecida");
				setConnection(connection);
			} else {
				JOptionPane.showMessageDialog(null, "Error al conectarse a la BD");

			}

		} catch (java.sql.SQLException sqle) {
			JOptionPane.showMessageDialog(null, "Error: " + sqle);
		}

	}

	static void insertarCoche(String marca, String modelo, String matricula, java.sql.Date fecha_matriculacion,
			String color, boolean hibrido, float precio) throws SQLException, NumberFormatException, ParseException {
		String sql = "INSERT INTO coches (marca, modelo, matricula, fecha_matriculacion, color, hibrido, precio) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = getConnection().prepareStatement(sql);
		statement.setString(1, marca);
		statement.setString(2, modelo);
		statement.setString(3, matricula);
		statement.setDate(4, fecha_matriculacion);
		statement.setString(5, color);
		statement.setBoolean(6, hibrido);
		statement.setFloat(7, precio);
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			JOptionPane.showMessageDialog(null, "¡Se ha insertado correctamente el nuevo coche!");
			mostrarMenu();
		} else {
			JOptionPane.showMessageDialog(null, "No se ha insertado el coche, comprueba el formato de los campos");
		}
	}

	static void actualizarCoche(String marca, String modelo, String matricula, java.sql.Date fecha_matriculacion,
			String color, boolean hibrido, float precio) throws SQLException, NumberFormatException, ParseException {

		String sql = "UPDATE coches SET marca=?, modelo=?, fecha_matriculacion=?, color=?, hibrido=?, precio=? WHERE matricula=? ";
		PreparedStatement statement = getConnection().prepareStatement(sql);
		statement.setString(1, marca);
		statement.setString(2, modelo);
		statement.setDate(3, fecha_matriculacion);
		statement.setString(4, color);
		statement.setBoolean(5, hibrido);
		statement.setFloat(6, precio);
		statement.setString(7, matricula);
		int rowsUpdated = statement.executeUpdate();

		if (rowsUpdated > 0) {
			JOptionPane.showMessageDialog(null,
					"¡Se ha actualizado correctamente el coche con matrícula " + matricula + "!");
			mostrarMenu();
		} else {
			JOptionPane.showMessageDialog(null,
					"No se ha podido actualizar el coche, asegúrate de que existan coches con la matrícula "
							+ matricula);
			mostrarMenu();
		}

	}

	static void borrarCoche(String matricula) throws SQLException {
		String sql = "DELETE FROM coches WHERE matricula=?";
		PreparedStatement statement = getConnection().prepareStatement(sql);
		statement.setString(1, matricula);

		int rowsDeleted = statement.executeUpdate();

		if (rowsDeleted > 0) {
			JOptionPane.showMessageDialog(null,
					"¡El coche con matrícula " + matricula + "se ha eliminado correctamente!");
		} else {
			JOptionPane.showMessageDialog(null,
					"No se ha podido eliminar el coche, asegúrate de que existan coches con la matrícula " + matricula);
		}

	}

	static void verCoche(String matricula) throws SQLException, NumberFormatException, ParseException {
		String sql = "SELECT * FROM coches WHERE matricula='" + matricula + "' ";

		Statement statement = getConnection().createStatement();
		ResultSet result = statement.executeQuery(sql);

		while (result.next()) {
			String marca = result.getString("marca");
			String modelo = result.getString("modelo");
			Date fecha_m = result.getDate("fecha_matriculacion");
			String color = result.getString("color");
			boolean hibrido = result.getBoolean("hibrido");
			float precio = result.getFloat("precio");
			String esHibrido;
			if (hibrido) {
				esHibrido = "Sí";
			} else {
				esHibrido = "No";
			}

			JOptionPane.showMessageDialog(null,
					"" + "Datos del coche con matrícula: " + matricula + "\n"
							+ "=====================================\n" + "Marca -> " + marca + "\n" + "Modelo -> "
							+ modelo + "\n" + "Fecha de matriculación -> " + fecha_m + "\n" + "Color -> " + color + "\n"
							+ "¿Es híbrido? -> " + esHibrido + "\n" + "Precio -> " + precio + "€\n");
		}
		mostrarMenu();

	}

	private static void datosCliente() throws SQLException, NumberFormatException, ParseException {
		JOptionPane.showMessageDialog(null, "TE VAMOS A DAR DE ALTA COMO NUEVO CLIENTE");
		String nombre = JOptionPane.showInputDialog("Introduce tu nombre:");
		String apellido = JOptionPane.showInputDialog("Introduce tu apellido/s:");
		String direccion = JOptionPane.showInputDialog("Introduce tu dirección:");
		Cliente cliente = new Cliente(nombre, apellido, direccion);
		insertarCliente(cliente);
	}

	private static void insertarCliente(Cliente cliente) throws SQLException, NumberFormatException, ParseException {
		String sql_insert = "INSERT INTO clientes (nombre, apellido, direccion) VALUES (?, ?, ?)";
		PreparedStatement statement_insert = getConnection().prepareStatement(sql_insert);
		statement_insert.setString(1, cliente.getNombre());
		statement_insert.setString(2, cliente.getApellido());
		statement_insert.setString(3, cliente.getDireccion());
		int rowsInserted = statement_insert.executeUpdate();
		if (rowsInserted > 0) {
			JOptionPane.showMessageDialog(null, "¡" + cliente.getNombre() + " has sido registrado correctamente!");
			borrarCoche();
		} else {
			JOptionPane.showMessageDialog(null, "Fallo al insertar el cliente " + cliente.getNombre());
			mostrarMenu();
		}
	}

	private static void borrarCoche() throws NumberFormatException, SQLException, ParseException {
		JOptionPane.showMessageDialog(null, "Se va a procesar tu compra ...");
		String sql_delete = "DELETE FROM coches WHERE id=?";
		PreparedStatement statement_delete = getConnection().prepareStatement(sql_delete);
		statement_delete.setString(1, getCocheSeleccionado());

		int rowsDeleted = statement_delete.executeUpdate();

		if (rowsDeleted > 0) {
			JOptionPane.showMessageDialog(null, "¡Enhorabuena tu compra se ha realizado correctamente");
			mostrarMenu();

		} else {
			JOptionPane.showMessageDialog(null,
					"Lo sentimos, ha fallado el proceso de compra. Asegúrate de que el identificador del coche era correcto");
		}
	}

	static void comprarCoche() throws SQLException, NumberFormatException, ParseException {
		String sql = "SELECT * FROM coches";

		Statement statement = getConnection().createStatement();
		ResultSet result = statement.executeQuery(sql);

		String cadenaCoches = "";

		if (!result.next()) {
			JOptionPane.showMessageDialog(null, "No hay coches disponibles");
			mostrarMenu();
		} else {
			do {
				int id = result.getInt("id");
				String marcas = result.getString("marca");
				String matriculas = result.getString("matricula");
				String modelos = result.getString("modelo");
				Date fechas_m = result.getDate("fecha_matriculacion");
				String colores = result.getString("color");
				boolean hibridos = result.getBoolean("hibrido");
				float precios = result.getFloat("precio");
				String sonHibridos;
				if (hibridos) {
					sonHibridos = "Híbrido";
				} else {
					sonHibridos = "No híbrido";
				}

				cadenaCoches += "(" + id + ") " + marcas + " " + modelos + " * " + matriculas + "* Matriculado el "
						+ fechas_m + "* De color " + colores + " * " + sonHibridos + " * " + precios + "€ \n";

				setCocheSeleccionado(JOptionPane.showInputDialog("COCHES DISPONIBLES\n" + "==========================\n"
						+ "=> Escribe el identificador del coche que quieras comprar: \n\n" + cadenaCoches + "\n"));
				datosCliente();
			} while (result.next());
		}
	}

	public static void mostrarMenu() throws NumberFormatException, SQLException, ParseException {

		String entrada = null;

		do {
			entrada = JOptionPane.showInputDialog("" + "BASE DE DATOS DE COCHES\n" + "========================\n"
					+ "1) INSERTAR NUEVO COCHE\n" + "2) ACTUALIZAR UN COCHE\n" + "3) BORRAR UN COCHE\n"
					+ "4) CONSULTAR UN COCHE\n" + "5) COMPRAR COCHE \n" + "0) SALIR\n\n");

			switch (entrada) {

			case "1":
				String marca = JOptionPane.showInputDialog("Marca del coche:");
				String modelo = JOptionPane.showInputDialog("Modelo del coche:");
				String matricula = JOptionPane.showInputDialog("Matrícula:");
				String fecha_matriculacion = JOptionPane.showInputDialog("Fecha de matriculación (DD-MM-AAAA):");
				String color = JOptionPane.showInputDialog("Color del coche:");
				String hibrido = JOptionPane.showInputDialog("¿Es híbrido? (Sí o no)");
				if (hibrido.equals("sí") || hibrido.equals("si") || hibrido.equals("Si") || hibrido.equals("s")
						|| hibrido.equals("Sí")) {
					hibrido = "1";
				} else {
					hibrido = "0";
				}
				String precio = JOptionPane.showInputDialog("Precio:");

				// Formatea el String al formato de una fecha
				DateFormat parser = new SimpleDateFormat("dd-MM-YYYY");
				Date fecha_formateada = parser.parse(fecha_matriculacion);

				// Convierte de java.utils.Date a java.sql.Date
				java.sql.Date fecha_m = new java.sql.Date(fecha_formateada.getTime());

				insertarCoche(marca, modelo, matricula, fecha_m, color, Boolean.parseBoolean(hibrido),
						Float.parseFloat(precio));
				break;

			case "2":
				String nuevaMatricula = JOptionPane
						.showInputDialog("Escribe la matrícula del coche que quieras actualizar:");
				String nuevaMarca = JOptionPane.showInputDialog("Nueva marca del coche:");
				String nuevoModelo = JOptionPane.showInputDialog("Nuevo modelo del coche:");
				String nuevaFecha_matriculacion = JOptionPane
						.showInputDialog("Nueva fecha de matriculación (DD-MM-AAAA):");
				String nuevoColor = JOptionPane.showInputDialog("Nuevo color del coche:");
				String nuevoHibrido = JOptionPane.showInputDialog("¿Es híbrido? (Sí o no)");
				if (nuevoHibrido.equals("sí") || nuevoHibrido.equals("si") || nuevoHibrido.equals("Si")
						|| nuevoHibrido.equals("s") || nuevoHibrido.equals("Sí")) {
					nuevoHibrido = "1";
				} else {
					nuevoHibrido = "0";
				}
				String nuevoPrecio = JOptionPane.showInputDialog("Nuevo Precio:");

				// Formatea el String al formato de una fecha
				DateFormat nuevoParser = new SimpleDateFormat("dd-MM-YYYY");
				Date nuevaFecha_formateada = nuevoParser.parse(nuevaFecha_matriculacion);

				// Convierte de java.utils.Date a java.sql.Date
				java.sql.Date nuevaFecha_m = new java.sql.Date(nuevaFecha_formateada.getTime());

				actualizarCoche(nuevaMarca, nuevoModelo, nuevaMatricula, nuevaFecha_m, nuevoColor,
						Boolean.parseBoolean(nuevoHibrido), Float.parseFloat(nuevoPrecio));
				break;

			case "3":
				String matriculaBorrar = JOptionPane
						.showInputDialog("Escribe la matrícula del coche que quieras eliminar:");
				borrarCoche(matriculaBorrar);
				break;

			case "4":
				String matriculaConsultar = JOptionPane
						.showInputDialog("Escribe la matrícula del coche que quieres ver:");
				verCoche(matriculaConsultar);
				break;

			case "5":
				comprarCoche();
				break;

			default:
				break;
			}

		} while (entrada == "0");

	}

	public static void main(String[] args) throws NumberFormatException, SQLException, ParseException {
		conectarBD();
		mostrarMenu();
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		MainJDBC.connection = connection;
	}

	public static String getCocheSeleccionado() {
		return cocheSeleccionado;
	}

	public static void setCocheSeleccionado(String cocheSeleccionado) {
		MainJDBC.cocheSeleccionado = cocheSeleccionado;
	}

}

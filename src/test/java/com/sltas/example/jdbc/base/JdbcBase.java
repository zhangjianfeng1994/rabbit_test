package com.sltas.example.jdbc.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sltas.order.domain.OrderHeader;
import com.sltas.order.util.id.OrderWorker;

/**
 * <p>
 * Title: JdbcBase.java
 * </p>
 * <p>
 * Description: 事务的特性：
			
			1) 原子性（atomicity）：事务是数据库的逻辑工作单位，而且是必须是原子工作单位，对于其数据修改，要么全部执行，要么全部不执行。
			
			2) 一致性（consistency）：事务在完成时，必须是所有的数据都保持一致状态。在相关数据库中，所有规则都必须应用于事务的修改，以保持所有数据的完整性。
			
			3) 隔离性（isolation）：一个事务的执行不能被其他事务所影响。
			
			4) 持久性（durability）：一个事务一旦提交，事物的操作便永久性的保存在DB中。即使此时再执行回滚操作也不能撤消所做的更改。
			
			事务(Transaction):是并发控制的单元，是用户定义的一个操作序列。这些操作要么都做，要么都不做，是一个不可分割的工作单位。通过事务，sql server 能将逻辑相关的一组操作绑定在一起，以便服务器 保持数据的完整性。
			事务通常是以begin transaction开始，以commit或rollback结束。Commint表示提交，即提交事务的所有操作。具体地说就是将事务中所有对数据的更新写回到磁盘上的物理数据库中去，事务正常结束。
			Rollback表示回滚，即在事务运行的过程中发生了某种故障，事务不能继续进行，系统将事务中对数据库的所有已完成的操作全部撤消，滚回到事务开始的状态。
			
			自动提交事务：每条单独的语句都是一个事务。每个语句后都隐含一个commit。 （默认）
			
			显式事务：以begin transaction显示开始，以commit或rollback结束。

			隐式事务：当连接以隐式事务模式进行操作时，sql server数据库引擎实例将在提交或回滚当前事务后自动启动新事务。无须描述事物的开始，只需提交或回滚每个事务。
			但每个事务仍以commit或rollback显式结束。连接将隐性事务模式设置为打开之后，当数据库引擎实例首次执行下列任何语句时，
			都会自动启动一个隐式事务：alter table，insert，create，open ，delete，revoke ，drop，select， fetch ，truncate table，grant，update在发出commit或rollback语句之前，
			该事务将一直保持有效。在第一个事务被提交或回滚之后，下次当连接执行以上任何语句时，数据库引擎实例都将自动启动一个新事务。该实例将不断地生成隐性事务链，直到隐性事务模式关闭为止。
 * 
 * 
 * 
 * 二、事务的并发问题

　　1、脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据

　　2、不可重复读：事务 A 多次读取同一数据，事务 B 在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取同一数据时，结果 不一致。

　　3、幻读：系统管理员A将数据库中所有学生的成绩从具体分数改为ABCDE等级，但是系统管理员B就在这个时候插入了一条具体分数的记录，当系统管理员A改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。

　　小结：不可重复读的和幻读很容易混淆，不可重复读侧重于修改，幻读侧重于新增或删除。解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表
		事务回滚后，自增ID仍然增加
 * 
 * 
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2019年5月29日 上午11:10:23  
 */
public class JdbcBase {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Test
	public void openSession() {
		
		String sql = "insert into order_header (id,trans_no,gmt_date) values(?,?,?)";
		
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		
		OrderWorker ow = OrderWorker.initNo(OrderWorker.TRANS_NO);
		OrderHeader oh = new OrderHeader();
		oh.setId(ow.getId());
		oh.setTransNo(ow.getNo());
		oh.setGmtDate(ow.getGmtDate());
		
		try {  
			conn.setAutoCommit(false); //将自动提交设置为false  
			
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setLong(1, oh.getId());
	        pstmt.setString(2, oh.getTransNo());
	        pstmt.setObject(3, oh.getGmtDate());
	        logger.info("{}", pstmt.executeUpdate());

//	        Integer.valueOf("abc");
	        
		    conn.commit();      //当两个操作成功后手动提交  

		} catch (Exception e) {  

		    try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    e.printStackTrace();  

		} finally {
			try {
				if (pstmt!=null) pstmt.close();
				if (conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Test
	public void baseOper() {
		
		OrderWorker ow = OrderWorker.initNo(OrderWorker.TRANS_NO);
		JdbcBase.getAll();
		OrderHeader oh = new OrderHeader();
		oh.setId(ow.getId());
		oh.setTransNo(ow.getNo());
		oh.setGmtDate(ow.getGmtDate());
		logger.info("{}",oh);
		JdbcBase.insert(oh);
		JdbcBase.getAll();
		oh.setOrderType(1);
		JdbcBase.update(oh);
		JdbcBase.getAll();
//		JdbcBase.delete(oh);
	}
	
	/**
	 * <p>
	 * Title: getConn
	 * </p>
	 * <p>
	 * Description: java.sql 接口 Connection
		所有超级接口：
		Wrapper
		public interface Connectionextends Wrapper
		 
		
		与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果。
		
		Connection 对象的数据库能够提供描述其表、所支持的 SQL 语法、存储过程、此连接功能等等的信息。此信息是使用 getMetaData 方法获得的。
	 * </p>
	 * @param @return 
	 * @return Connection
	 * @throws
	 * @author 周顺宇 
	 * @date 2019年5月29日 上午11:07:57 
	 */
	private static Connection getConn() {
	    String driver = "com.mysql.cj.jdbc.Driver";
	    String url = "jdbc:mysql://127.0.0.1:3306/canal_test?rewriteBatchedStatements=true&serverTimezone=GMT%2B8&useSSL=false=";
	    String username = "root";
	    String password = "root";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
//	        conn.setAutoCommit(false); //将自动提交设置为false  
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	
	/**
	 * <p>
	 * Title: insert
	 * </p>
	 * <p>
	 * Description: java.sql 接口 PreparedStatement
		所有超级接口：
		Statement, Wrapper
		所有已知子接口：
		CallableStatement
		public interface PreparedStatementextends Statement
		表示预编译的 SQL 语句的对象。
		
		SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		
		常用方法
		
		 boolean  execute()
		
		          在此 PreparedStatement 对象中执行 SQL 语句，该语句可以是任何种类的 SQL 语句。
		
		 ResultSet  executeQuery()
		
		          在此 PreparedStatement 对象中执行 SQL 查询，并返回该查询生成的 ResultSet 对象。
		
		 int  executeUpdate()
		
		          在此 PreparedStatement 对象中执行 SQL 语句，该语句必须是一个 SQL 数据操作语言（Data Manipulation Language，DML）语句，比如 INSERT、UPDATE 或 DELETE 语句；或者是无返回内容的 SQL 语句，比如 DDL 语句。
	 * </p>
	 * @param @param oh
	 * @param @return 
	 * @return int
	 * @throws
	 * @author 周顺宇 
	 * @date 2019年5月29日 上午11:08:27 
	 */
	private static int insert(OrderHeader oh) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into order_header (id,trans_no,gmt_date) values(?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setLong(1, oh.getId());
	        pstmt.setString(2, oh.getTransNo());
	        pstmt.setObject(3, oh.getGmtDate());
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	private static int update(OrderHeader oh) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "update order_header set order_type ='" + oh.getOrderType() + "' where id ='" + oh.getId() + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	/**
	 * <p>
	 * Title: getAll
	 * </p>
	 * <p>
	 * Description: java.sql 接口 ResultSet
		所有超级接口：
		Wrapper
		所有已知子接口：
		CachedRowSet, FilteredRowSet, JdbcRowSet, JoinRowSet, RowSet, SyncResolver, WebRowSet
		public interface ResultSetextends Wrapper
		表示数据库结果集的数据表，通常通过执行查询数据库的语句生成。
	 * </p>
	 * @param @return 
	 * @return Integer
	 * @throws
	 * @author 周顺宇 
	 * @date 2019年5月29日 上午11:09:13 
	 */
	private static Integer getAll() {
	    Connection conn = getConn();
	    String sql = "select * from order_header";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("============================");
	        while (rs.next()) {
	            for (int i = 1; i <= col; i++) {
	                System.out.print(rs.getString(i) + "\t");
	                if ((i == 2) && (rs.getString(i).length() < 8)) {
	                    System.out.print("\t");
	                }
	             }
	            System.out.println("");
	        }
	            System.out.println("============================");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private static int delete(OrderHeader oh) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from order_header where id ='" + oh.getId() + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}

	
	/**
	 * JDBC的事务支持
			JDBC对事务的支持体现在三个方面：
			1.自动提交模式(Auto-commit mode)
			Connection提供了一个auto-commit的属性来指定事务何时结束。
			a.当auto-commit为true时，当每个独立SQL操作的执行完毕，事务立即自动提交，也就是说每个SQL操作都是一个事务。
			一个独立SQL操作什么时候算执行完毕，JDBC规范是这样规定的：
			对数据操作语言(DML，如insert,update,delete)和数据定义语言(如create,drop)，语句一执行完就视为执行完毕。
			对select语句，当与它关联的ResultSet对象关闭时，视为执行完毕。
			对存储过程或其他返回多个结果的语句，当与它关联的所有ResultSet对象全部关闭，所有update count(update,delete等语句操作影响的行数)和output parameter(存储过程的输出参数)都已经获取之后，视为执行完毕。
			b. 当auto-commit为false时，每个事务都必须显示调用commit方法进行提交，或者显示调用rollback方法进行回滚。auto-commit默认为true。
			JDBC提供了5种不同的事务隔离级别，在Connection中进行了定义。
			
			2.事务隔离级别(Transaction Isolation Levels)
			JDBC定义了五种事务隔离级别：
			TRANSACTION_NONE JDBC驱动不支持事务
			TRANSACTION_READ_UNCOMMITTED 允许脏读、不可重复读和幻读。
			TRANSACTION_READ_COMMITTED 禁止脏读，但允许不可重复读和幻读。
			TRANSACTION_REPEATABLE_READ 禁止脏读和不可重复读，单运行幻读。
			TRANSACTION_SERIALIZABLE 禁止脏读、不可重复读和幻读。
			
			3.保存点(SavePoint)
			JDBC定义了SavePoint接口，提供在一个更细粒度的事务控制机制。当设置了一个保存点后，可以rollback到该保存点处的状态，而不是rollback整个事务。
			Connection接口的setSavepoint和releaseSavepoint方法可以设置和释放保存点。
			
			JDBC规范虽然定义了事务的以上支持行为，但是各个JDBC驱动，数据库厂商对事务的支持程度可能各不相同。如果在程序中任意设置，可能得不到想要的效果。为此，JDBC提供了DatabaseMetaData接口，提供了一系列JDBC特性支持情况的获取方法。比如，通过DatabaseMetaData.supportsTransactionIsolationLevel方法可以判断对事务隔离级别的支持情况，通过DatabaseMetaData.supportsSavepoints方法可以判断对保存点的支持情况。
	 */
	
}

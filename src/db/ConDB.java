package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.JDBCUtils;

/**
 * 连接数据库
 * @author Administrator
 *
 */
public class ConDB {
	Connection conn = null;
	
	public ConDB() throws SQLException{
		conn = JDBCUtils.getConnection();
	}
	
	/*
	public void main(String[] args) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        conn = JDBCUtils.getConnection();
        String sql = "insert into category values (?,?)";
        st= conn.prepareStatement(sql);
        st.setString(1, "10");
        st.setString(2, "测试目录");
        int i = st.executeUpdate();
            
        if(i==1) {
            System.out.println("数据添加成功！");
        }else {
            System.out.println("数据添加失败！");
        }
	}*/
	
	public ResultSet select(String sql,Object ... data) throws SQLException {
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet rs = null;
		
		for(int i=0;i<data.length;i++) {
			st.setObject(i+1, data[i]);
		}
		
		
		rs = st.executeQuery();
		
		return rs;
	}
	
	public boolean upDataOrInsert(String sql,Object ...data) throws SQLException {
		PreparedStatement st = conn.prepareStatement(sql);
		for(int i=0;i<data.length;i++) {
			st.setObject(i+1, data[i]);
		}
		int i = st.executeUpdate();
        if(i==1) {
            return true;
        }else {
            return false;
        }
	}
	
	public boolean upDataOrInsertResu(String sql,Object ...data) throws SQLException {
		PreparedStatement st = conn.prepareStatement(sql);
		for(int i=0;i<data.length;i++) {
			st.setObject(i+1, data[i]);
		}
		int i = st.executeUpdate();
        if(i==1) {
            return true;
        }else {
            return false;
        }
	}
}

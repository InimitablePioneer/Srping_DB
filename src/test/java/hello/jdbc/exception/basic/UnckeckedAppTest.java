package hello.jdbc.exception.basic;

import java.sql.SQLException;

public class UnckeckedAppTest {

    static class Repository {
        public void call() {

        }



        private void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }


    static class RuntimeConnectionException extends RuntimeException {
        public RuntimeConnectionException(String message) {
            super(message);
        }
    }




    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}

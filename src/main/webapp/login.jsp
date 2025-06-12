<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <%@include file="base-head.jsp" %>
    <style>
      html, body {
        height: 100%;
        margin: 0;
      }
      .full-height-container {
        min-height: 100vh;
        display: flex;
        flex-direction: column;
      }
      .centered-content {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .login-card {
        width: 100%;
        max-width: 400px;
      }
    </style>
  </head>
  <body>
    <div class="full-height-container">
      
      <%-- Conteúdo centralizado --%>
      <div class="centered-content">
        <div class="card login-card shadow-sm p-4">
          <h2 class="text-center mb-4">Login</h2>
          <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="mb-3">
              <label for="user_login_id" class="form-label">Login (e-mail)</label>
              <input type="text" class="form-control" id="user_login_id" name="user_login" required />
            </div>

            <div class="mb-3">
              <label for="user_pw_id" class="form-label">Senha</label>
              <input type="password" class="form-control" id="user_pw_id" name="user_pw" required />
            </div>

            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark btn-lg">Logar</button>
            </div>

            <c:if test="${param.erro == 'true'}">
              <span class="text-danger small">Usuário ou senha inválidos.</span>
            </c:if>
          </form>
        </div>
      </div>

      <%-- Footer fixo na parte inferior --%>
      <footer class="bg-dark text-white text-center py-3">
        <jsp:useBean id="date" class="java.util.Date" />
        <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
        <p class="mb-0">© <c:out value="${currentYear}" /> CrudManager. Todos os direitos reservados.</p>
      </footer>
      
    </div>

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  </body>
</html>

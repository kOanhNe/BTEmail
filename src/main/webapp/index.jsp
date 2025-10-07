<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Murach's Java Servlets and JSP</title>
<style>
/* Reset and base styles */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    font-size: 100%;
    background: linear-gradient(135deg, #0f2027 0%, #203a43 50%, #2c5364 100%);
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 2em;
}

.container {
    background: rgba(255, 255, 255, 0.95);
    padding: 3em;
    border-radius: 20px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5);
    max-width: 600px;
    width: 100%;
    backdrop-filter: blur(10px);
}

h1 {
    font-size: 2em;
    color: #2c5364;
    margin-bottom: 1em;
    text-align: center;
    font-weight: 600;
}

p {
    color: #555;
    line-height: 1.8;
    margin-bottom: 1.5em;
    font-size: 1.1em;
}

.form-group {
    margin-bottom: 1.5em;
    display: flex;
    align-items: center;
}

label {
    font-weight: bold;
    color: #2c5364;
    min-width: 120px;
    font-size: 1.1em;
}

input[type="text"], input[type="email"] {
    flex: 1;
    padding: 0.8em;
    border: 2px solid #ddd;
    border-radius: 8px;
    font-size: 1em;
    transition: all 0.3s ease;
    background: #f8f9fa;
}

input[type="text"]:focus, input[type="email"]:focus {
    outline: none;
    border-color: #2c5364;
    background: #ffffff;
    box-shadow: 0 0 0 3px rgba(44, 83, 100, 0.1);
}

.button-group {
    text-align: center;
    margin-top: 2em;
}

input[type="submit"] {
    background: linear-gradient(135deg, #2c5364 0%, #0f2027 100%);
    color: #ffffff;
    padding: 1em 3em;
    border: none;
    border-radius: 50px;
    font-weight: bold;
    font-size: 1.1em;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 5px 15px rgba(44, 83, 100, 0.3);
}

input[type="submit"]:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(44, 83, 100, 0.5);
}

input[type="submit"]:active {
    transform: translateY(-1px);
}

i {
    color: #e74c3c;
    display: block;
    text-align: center;
    margin-top: 1em;
    font-size: 1em;
}

/* Footer version text */
.version {
    position: fixed;
    bottom: 20px;
    left: 20px;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.6);
    font-weight: 500;
}
</style>
</head>
<body>
<div class="container">
    <h1>Join our email list</h1>
    <p>To join our email list, enter your name and
    email address below.</p>
    <p><i>${message}</i></p>
    <form action="emailList" method="post">
        <input type="hidden" name="action" value="add">
        
        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" value="${user.email}" required>
        </div>
        
        <div class="form-group">
            <label>First Name:</label>
            <input type="text" name="firstName" value="${user.firstName}" required>
        </div>
        
        <div class="form-group">
            <label>Last Name:</label>
            <input type="text" name="lastName" value="${user.lastName}" required>
        </div>
        
        <div class="button-group">
            <input type="submit" value="Join Now">
        </div>
    </form>
</div>

<div class="version">kol-version</div>
</body>
</html>
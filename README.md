## Tax return
5. Система Подачи Отчетов в Налоговую. Физ./Юр.лицо (далее
Пользователь) регистрируется. Подает отчет (XML/JSON/Форма).
Налоговый Инспектор принимает/отклоняет отчет (указывая причину
отказа). Пользователь может просмотреть все поданные отчеты, причины
отказа и изменять их если того потребовал Инспектор. Пользователь может
отправлять запрос на замену Инспектора в случае неудовлетворения.

### Setup 
* JDK 1.8 or higher
* Apache Maven 3.6.1 or higher
* MySQL 5.7 or higher
* Apache Tomcat 7.0.93 or higher

### Installation
* Clone project from GitHub (_git clone https://github.com/SweetSquid/Tax-Return_)
* Specify values of "mysql.user" and "mysql.password" keys in _../src/main/resources/database/connection.properties_
* Execute script _../sql/finalproject.sql_ to create database
* cd to root project folder and execute command _mvn clean install_
* copy artifact ROOT.war from _target_ folder to _%TOMCAT%\webapps_

### Running
* Start Tomcat server by running the script _%TOMCAT%\bin\startup_ .bat for Windows or .sh for Unix based OS.
* After server start, application will be available by URL _http://localhost:8080/taxreturn_
* Use login _**"admin"**_ and password _**"1"**_ to log in with administrator rights.
* Use login _**"inspector0"**_ or _**"inspector1"**_ or _**"inspector2"**_
  and password _**"1"**_ to log in with inspector rights.
* To stop server run script _%TOMCAT%\bin\shutdown_ .bat for Windows or .sh for Unix based OS.

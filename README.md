<h2>UPDATE: This was my first larger application written in Spring. Unfortunately, it contains many errors and bad programming practices. Fortunately, I am learning quickly and drawing conclusions. Currently, I am creating a new version of this application that will have many more features. </br>
<p><a href="https://github.com/maciej-jankowskii/TSL-2.0">Click here -> TSL 2.0</a></p>
</h2>
<h1 align="center" id="title">TSL COMPANY</h2>


<p id="description">I leveraged my years of experience in the transportation industry to create a project for managing a small freight company.<br><br>
  The application is written in <b>Java</b> using the <b>Spring framework</b>. To secure the application I used Spring Security. I also used the following technologies in the project: Spring Data JPA, Lombok and I chose the MySQL database and for migration I used Liquibase. Tests were conducted using the jUnit and Mockito libraries. I also used simple HTML CSS and Thymeleaf to see the immediate results of my work although this is just an addition.<br><br>
  The application allows for user login and registration. It has three main panels: Forwarder Panel, Accounting and Management.<br>
  The Forwarder Panel allows to add contractors: carriers and clients who assign transportation tasks to us. We can add cargo and create orders based on them. We have the ability to view all cargo and orders as well as change their statuses.<br>
  The Accounting Panel allows adding invoices from carriers. A simulation of invoice payment has been introduced which automatically reduces the balance of a contractor. We can also filter invoices. Similar capabilities exist in the invoices section for clients. Additionally we can send reminders if the payment deadline has passed using a mock email inbox. The last section in this panel is the results of forwarders. We can check the margins achieved by a employee.<br>
  The last panel is the management panel. We can assign new roles to newly registered employees and also remove them.<br><br>Feel free to explore the project. For more information below.</p>

<h2>🛠️ Installation Steps:</h2>

<p>1. You need to clone this repository.</p>

```
git clone https://github.com/maciej-jankowskii/tsl-company.git
```

<p>2. Remember to create a database schema locally and configure the 'application.yml' or 'application.properties' file before running it. You should provide your own credentials in that file.</p>

<p>6. Now you can run the project. You can start using it by entering the following address in your browser:."</p>

```
localhost:8080
```

<h2>How it works ?</h2>
<p> 
  The first thing you will see is the login panel. You can use the credentials that have already been prepared. You can find them in the <b>LOG-IN DATA</b> file in resources. This is a user in the database with an administrator role. They can perform all actions in the application. However, if you do not want to use the pre-prepared data, you can register yourself. Remember that even if you do this, you still need to log in as an administrator and then assign yourself a new role in the management panel.
Screenshots below.
</p>
<img src="https://github.com/maciej-jankowskii/tsl-company/blob/5e27df3c133f3613562c95dfd4127085cce785d9/start.png" alt="project-screenshot" width="600" height="350/">
<img src="https://github.com/maciej-jankowskii/tsl-company/blob/a017eb99f06ca2c47c9ff37edd7b07b362422eb3/roles.png" alt="project-screenshot" width="600" height="350/">
<p>
  <p id="description">Enter the ID of the newly registered user then enter one of the roles:<br><br>-FORWARDER<br>-ACCOUNTANT<br>-MANAGEMENT<br><br>Remember that a Forwarder only has access to the forwarding panel and an Accountant only has access to the accounting panel. Choosing one of these roles may limit your access to all the application's features.</p>
</p>
</br></br>
___________________________
<h3>Let's simulate the work of a real forwarder!</h3>
<p>
  I recommend starting by adding cargo that you received from a client. You can do this in the forwarder panel. I suggest using the available contractors. Of course, if you prefer, you can create your own client and carrier.
</p>
<img src="https://github.com/maciej-jankowskii/tsl-company/blob/7f8567b25f5d233ed0212bfe400b4dad7c188b1f/addcargo.png" alt="project-screenshot" width="270" height="380/">
<p>
  When you added cargo from the client, their balance in the database was updated. You can check this in your database. Now you can create an order.
</p>
<img src="https://github.com/maciej-jankowskii/tsl-company/blob/fa1a543ad7b73dc9506c9e44e4f66dca0837e926/addorder.png" alt="project-screenshot" width="270" height="380/">
<p>
  Please note that after creating an order and assigning it to the carrier, their balance also changed. Now you can browse all cargos, all orders, edit them, and change their statuses.
</p>
<h3>"Maybe you'd like to get to know the accountant's work?"</h3>
<p>
  Let's speed things up a bit and simulate a situation where the transport has already taken place, and everything ended successfully. The carrier has sent us an invoice for the service rendered.
</br>
In the application, locate the Accounting panel and try to add a new invoice.
</p>
<img src="https://github.com/maciej-jankowskii/tsl-company/blob/15a2d287968e1bf7ee24316a33f851d3714ce681/carrierinvoice.png" alt="project-screenshot" width="270" height="380/">
</br>
<p>
Now you can view all the invoices in your panel. The application allows you to filter invoices, which is useful when there are many of them. You can choose to see only unpaid or paid invoices. Note that the application has automatically calculated the payment due date for each invoice. You can also simulate paying the invoice, which will reduce the carrier's balance by the appropriate amount.
</br>
Look below.  
</p>
<img src="https://github.com/maciej-jankowskii/tsl-company/blob/c6390f0e7fad4246c80cbca8ffb8c31d38afa16a/invoices.png" alt="project-screenshot" width="600" height="200/">
</br>
<p>The same situation applies to the 'Invoices for Clients' section. This is where we enter invoices for clients. The difference is that when a client has paid the invoice, we can mark it as paid or send a fake payment reminder. This is just a simulated email. We simulate sending an email to the client, asking, 'Why haven't you paid yet?!' :))
</br>
In the last section, you can check the results of the forwarders. Each order is assigned to the forwarder who creates it. And on each order, we make some margin (I hope).
</br>
The last panel is for management. You've encountered it at the very beginning. You can assign roles to new employees there or even remove employees if someone isn't following your instructions.
</p>
___________________________
<h3>Enjoy 👋 </h3>

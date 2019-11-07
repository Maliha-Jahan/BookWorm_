            function validate_form() {
                if (document.getElementById("npassword").value().length !== 0){
                if (!(/\d/.test(document.getElementById("regform:npassword").value()) && /[a-z]+/.test(document.getElementById("regform:npassword").value()) && /[A-Z]+/.test(document.getElementById("regform:npassword").value()))) {
                    alert("Password must contain atleast one letter, small letter, capital letter");
                    return false;
                }
                if (document.getElementById("regform:npassword").value().length < 8 || document.getElementById("regform:npassword").value().length > 20) {
                    alert("Password must be 8-20 characters long");
                    return false;
                }
                alert("sucessfully Submitted");

            } }  


/*<script language="javascript">
    function validate_form() {
        if (document.getElementById("regform:npassword").value().length !== 0){
        if (!(/\d/.test(document.getElementById("regform:npassword").value()))) {
            alert("Password must contain atleast one letter, small letter, capital letter");
            return false;
        }
        if (!(/[a-z]+/.test(document.getElementById("regform:npassword").value()))) {
            alert("Password must contain atleast one letter, small letter, capital letter");
            return false;
        }
        if (!(/[A-Z]+/.test(document.getElementById("regform:npassword").value()))) {
            alert("Password must contain atleast one letter, small letter, capital letter");
            return false;
        }
        if (document.getElementById("regform:npassword").value().length < 8) {
            alert("Password must be 8-20 characters long");
            return false;
        }
        if (document.getElementById("regform:npassword").value().length > 20) {
            alert("Password must be 8-20 characters long");
            return false;
        }
        alert("sucessfully Submitted");

    } }  
</script>
             <?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
    <h:head>
        <title>Facelet Title</title>
    </h:head>
    <h:body>
        <br></br><br></br><br></br><br></br><br></br><br></br>
        <f:view>


            <h:form>
                <p align="center">
                <h4><h:outputText value="Registration Form"/></h4>
                <h:panelGrid columns="2">
                    <h:outputLabel value="FirstName:" for="firstName" />
                    <h:inputText id="firstName" value="#{signupController.profile.firstName}" title="FirstName" required="true" requiredMessage="The FirstName field is required."/>
                    <h:outputLabel value="LastName:" for="lastName" />
                    <h:inputText id="lastName" value="#{signupController.profile.lastName}" title="LastName" required="true" requiredMessage="The LastName field is required."/>
                    <h:outputLabel value="Age:" for="age" />
                    <h:inputText id="age" value="#{signupController.profile.age}" title="Age" required="true" requiredMessage="The Age field is required."/>
                    <h:outputLabel value="Email:" for="email" />
                    <h:inputText id="email" value="#{signupController.profile.email}" title="Email" required="true" requiredMessage="The email field is required." validatorMessage="email is not valid">
                        <f:validateRegex pattern="[\w\.-]*[a-zA-Z0-9_]@[\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]" />
                    </h:inputText>
                    <h:outputLabel value="Gender:" for="gender" />
                    <h:selectOneMenu id="gender" value="#{signupController.profile.gender}" title="Gender" required="true" requiredMessage="The gender field is required.">
                        <f:selectItem itemValue = "Male" itemLabel = "Male" /> 
                        <f:selectItem itemValue = "Female" itemLabel = "Female" /> 
                    </h:selectOneMenu>
                    <h:outputLabel value="UserName:" for="userName" />
                    <h:inputText id="userName" value="#{signupController.login.userName}" title="UserName" required="true" requiredMessage="The UserName field is required."/>
                    <h:outputLabel value="Password:" for="password" />
                    <h:inputSecret id="password" value="#{signupController.login.password}" title="Password" required="true" requiredMessage="The password field is required."/>
                    
                    <h:commandButton value="save" action="#{signupController.add()}"></h:commandButton>
                    
                </h:panelGrid>
            </h:form>
            </p>
        </f:view>

    </h:body>
</html>


             */
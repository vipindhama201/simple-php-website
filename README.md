# DevOps Project on CI/CD pipeline 
## Simple PHP website using the following tools Github, Jenkins, Docker , Ansible , selinium on Google Cloud
*This is a devops project to demonstrate the usage of DevOps tools to create a Simple PHP Website (source code for the website is from https://github.com/banago/simple-php-website)*


## CI/CD pipeline
Below mentioned is the lab set up for this project
#### Lab setup

![Lab setup](https://github.com/Raveendiran-RR/simple-php-website/blob/master/images/lab-Setup.PNG)

#### Workflow
 ![Workflow](https://github.com/Raveendiran-RR/simple-php-website/blob/master/images/workflow.PNG)
 
 1. When the developer commits code into the master branch, the workflow kicks in
 2. A docker image (refer dockerfile for more details)is created with the latest website code in it and tests are run on a test server using selenium (test.java contains the code for the test)
 3. When the tests are successfull, the code is uploaded to dockerhub
 4. In this project, the successfull tested docker image is deployed to production.However in the real world scenario it, it is advisable to review manually and then deploy to production



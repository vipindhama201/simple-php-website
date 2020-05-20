#!/usr/bin/env groovy

// Maintainer : Raveendiran RR
// Script to deploy a simple PHP website pipeline
// 1. Download Config from GIt repo - master node
// 2. Update the slave machine with docker and Open JDK - From master node using Ansible
// 3. Download the Simple-PHP-Website code - Slave Node / Test Server
// 4. Deploy it on docker container named website-test and create image with build number as tag
// 5. Run the test using the Final_test.jar file
// 6. if the test result is a failure delete the container and image that was created
// 7. If the test result is a success deploy the changes in the production server 

import java.net.URL

// variable to store test result, declared globally
def test_result =''

node ('master')
    {
        try
        {
            stage('Download configuration files (Playbook, groovy script) from git repo - Master server')
            {
                echo'===========Download config from Git repo============='            
                    git branch: 'config', url: 'https://github.com/Raveendiran-RR/simple-php-website.git'
                }
                stage('Run Ansible playbook (from Master Server) to install docker on test server')
                {
                    echo 'install docker on test server'
                    sh 'sudo ansible-playbook ansible_updated_playbook.yml'
                }
        }
        catch(Exception err)
        {
                echo '****** Error ********'
                echo '${err}'
        }
        finally
        {
                echo 'script on master completed'
        }
    }
    
    
node ('slave')
    {
        try
        {
            stage ('Download PHP-Website files from git repo - Test Server')
            {
                echo'======Download PHP-Website files========'
                    git 'https://github.com/Raveendiran-RR/simple-php-website.git'
            }
                
            stage('Build / deploy to Test server stage ')
            {
                echo '===============Build Image and Deploy================='
                echo '****checking if container exists and remove it ****'
                // if the remove container command fails, tweek the shell out put as success to continue the execution
                sh 'sudo docker rm -f website-test || true'
                //build the latest image
                sh 'sudo docker build . -t smartbond/simple-php-website:v${BUILD_NUMBER} '
                //start the container 
                sh 'sudo docker run -itd -p 80:80 --name website-test smartbond/simple-php-website:v${BUILD_NUMBER}'               
            }
                
            stage ('Test about page')
            {
                echo '===========Testing============'
                test_output = sh (script: 'java -jar Final_test.jar ', , returnStdout:true).trim()
                if(test_output=="PASS")
                {
                    echo 'test passed !!! '
                    // if the test is successful push the image to docker registry
                    sh 'sudo docker push smartbond/simple-php-website:v${BUILD_NUMBER}'
                    test_result="PASS"
                }
                else
                {
                    echo ' test failed. '
                    sh ' sudo docker rm -f website-test || true'
                    echo '--- container deleted --------'
                    sh ' sudo docker rmi -f smartbond/simple-php-website:v${BUILD_NUMBER} || true '
                    echo '------- image deleted --------'
                }
                    
    
                    
                    
            }
            
        }

        catch(Exception err)
        {
            echo '****** Error ********'
            echo "${err}"
            echo 'code has errors. Deleting the image smartbond/simple-php-website:v${BUILD_NUMBER} and running images'
            sh'sudo docker rm -f website-test || true'
            sh'sudo docker rmi -f smartbond/simple-php-website:v${BUILD_NUMBER}' || true
        }
            finally
        {
            echo 'script on slave completed'
        }
    }
node('master')
    {
        stage('deploy to production')
        {
            if(test_result=="PASS")
            {
                echo 'deploying the latest image in production'
                //execute the ansible playbook to deploy the code on production
                //sh 'ansible-playbook Deploy-prod-website.yml'                
                sh 'sudo docker rm -f website-prod ||true'
                sh 'sudo docker run -itd --name website-prod -p 80:80 smartbond/simple-php-website:v${BUILD_NUMBER}'
            }
            else
            {
                echo 'Since tests failed, no changes in production'
            }
        }


    }


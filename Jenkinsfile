pipeline {
    agent any

    environment {
        GITHUB = "https://github.com/Walmett/DevOpsHomework.git"
        DIRECTORY = "DevOpsHomework"
        PATH="$PATH:/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/maven/bin/"
    }

    stages {
        stage('Clone') {
            steps {
                sh '''
                git clone $GITHUB
                cd $DIRECTORY
                git checkout Lab4
                '''
            }
        }

        stage('Build') {
            steps {
                script {
                    sh '''
                    cd $DIRECTORY/app
                    mvn clean package
                    
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh '''
                    cd $DIRECTORY/app
                    mvn test
                    
                    '''
                }
            }
        }
        
        stage('Allure') {
            steps {
                script {
                    sh '''
                    cd $DIRECTORY/app
                    mvn org.apache.maven.plugins:maven-dependency-plugin:2.10:tree -Dverbose=true
                    mvn allure:report
                    '''
                }
            }
        }

        stage('Sonar') {
            steps {
                script {
                    sh '''
                    cd $DIRECTORY/app
                    mvn sonar:sonar 
                        -Dsonar.projectKey=project_key 
                        -Dsonar.projectName=java_project
                        -Dsonar.projectVersion=1.0
                        -Dsonar.host.url=http:127.0.0.1:9000
                    '''
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    sh '''
                    cd $DIRECTORY/app
                    
                    touch Dockerfile
                    echo FROM openjdk:17 > Dockerfile
                    echo EXPOSE 8080 >> Dockerfile
                    echo ADD target/app-1.0-SNAPSHOT.jar deploy.jar >> Dockerfile
                    echo ENTRYPOINT [\"\"] >> Dockerfile
            
                    docker build -t deploy-image:latest .
                    docker tag deploy-image:latest walmet/deploy-image:deploy-image
                    docker push walmet/deploy-image:deploy-image
                    
                    '''
                }
            }
        }
    }

    post {
        always {
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: '$DIRECTORY/app/target/allure-results']]
            ])

            sh 'rm -rf $DIRECTORY'

            sh 'docker rmi walmet/deploy-image:latest'
        }

        success {
            echo 'Finished: SUCCESS'
        }

        failure {
            echo 'Finished: FAILURE'
        }
    }
}

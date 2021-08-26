  pipeline{
    agent any
    //定义仓库地址
    environment {
        REPOSITORY="https://github.com/emeraqqq/demo.git"
    }

    stages {

        stage('check out code'){
            steps {
                echo "start fetch code from git:${REPOSITORY}"
                // 清空当前目录
                deleteDir()
                // 拉取代码
                git "${REPOSITORY}"
                // 清空之前的容器
                sh 'docker stop $(docker ps -a -q)'
                sh 'docker rm $(docker ps -a -q)'
            }
        }

        stage('Maven Build'){
            steps {
                echo "start compile"
                // 切换目录
                dir('demo') {
                    // 重新打包
                    sh 'mvn -Dmaven.test.skip=true -U clean install'
                }
            }
        }

        stage('Build docker image'){
            steps {
                echo "start build image"
                dir('demo') {
                    // build image
                    sh 'docker build -t todorex/springboot_docker_git_jenkins_demo:1.0 .'
                    // 登录镜像仓库
                    sh 'docker login -u your_username -p your_password'
                    // push镜像到镜像仓库
                    sh 'docker push todorex/springboot_docker_git_jenkins_demo:1.0'
                }
            }
        }

        stage('start'){
            steps {
                echo "start demo"
                // 部署服务
                sh 'docker run -d -p 8888:8888 --name=demo todorex/springboot_docker_git_jenkins_demo:1.0'
            }
        }

    }
}
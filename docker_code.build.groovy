def CODE_REPO="https://github.com/setevoy2/tg.git"
def BRANCH="master"
def ENVIRONMENT="dev"

def DOCKER_CREDENTIALS_ID="setevoyDocHub"
def DOCKER_REGISTRY_ID=""
def DOCKER_REGISTRY_URL="https://hub.docker.com/"
def DOCKER_REGISTRY_HOST="hub.docker.com"
def DOCKER_REGISTRY_USERNAME=""
def DOCKER_REGISTRY_PASSWORD=""
def DOCKER_IMAGE_PATH=""
def DOCKER_IMAGE_TAG="${ENVIRONMENT}-${env.BUILD_NUMBER}"

node {

    stage ('Maven package') {

        docker.image('maven').inside {

            git branch: "${BRANCH}", credentialsId: 'githubSetevoy', url: "${CODE_REPO}"

            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: "${DOCKER_CREDENTIALS_ID}", 
            usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {

                sh 'echo uname=$USERNAME pwd=$PASSWORD'
   
                sh 'mvn clean install -DskipDockerBuild'

                sh "mvn clean package \
                  -Ddocker.registry.id=${DOCKER_REGISTRY_ID} \
                  -Ddocker.registry.host=${DOCKER_REGISTRY_HOST} \
                  -Ddocker.registry.url=${DOCKER_REGISTRY_URL} \
                  -Ddocker.registry.username=${DOCKER_REGISTRY_USERNAME} \
                  -Ddocker.registry.password=${DOCKER_REGISTRY_PASSWORD} \
                  -Ddocker.image.tag=\"${DOCKER_IMAGE_TAG}\" \
                  -DpushImage"
            }
        }
    }
}

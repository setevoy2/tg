node {

    stage('Creds test') {

        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'setevoyDocHub',
        usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {

            sh '''
              echo "User: $USERNAME, password: $PASSWORD"
            '''
        }
    }
}

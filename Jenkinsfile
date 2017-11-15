node {
    stage("Checkout & Prepare") {
        checkout scm
    }
    stage("Build") {
        sh "./gradlew clean build -x test -x checkstyleMain -x checkstyleTest"
    }
    stage("Check code style") {
        sh "./gradlew checkstyleMain checkstyleTest -x test"
    }
    stage("Unit tests") {
        sh "./gradlew test jacocoTestReport -i"
    }
    stage("Integration tests") {
        sh "./gradlew :bitcoin-integration-tests:test -Prun-integr-test=true -i"
    }
    stage("Sonar") {
        withCredentials([
            string(credentialsId: 'SONAR_HOST_URL', variable: 'SONAR_HOST_URL')
        ]) {
            sh "./gradlew sonarqube -i -PsonarUrl=$SONAR_HOST_URL"
        }
    }
    stage("Publish") {
        withCredentials([
            [ $class: 'UsernamePasswordMultiBinding', credentialsId: 'ARTIFACTORY_CREDENTIALS', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD' ],
            string(credentialsId: 'ARTIFACTORY_HOST', variable: 'ARTIFACTORY_HOST')
        ]) {
            sh "./gradlew publish -PartifactoryUsername=$USERNAME -PartifactoryPassword=$PASSWORD -PartifactoryHost=$ARTIFACTORY_HOST"
        }
    }
}

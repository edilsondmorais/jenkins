# Dockerfile

FROM jenkins/jenkins:lts

COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt
RUN  git config --global http.sslVerify false

COPY install-plugins.sh /usr/local/bin/install-plugins.sh

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

COPY seedjob.groovy /usr/local/seedjob.groovy
COPY seedMultibranchJob.groovy /usr/local/seedMultibranchJob.groovy

COPY jenkins-casc.yaml /usr/local/jenkins-casc.yaml
ENV CASC_JENKINS_CONFIG /usr/local/jenkins-casc.yaml
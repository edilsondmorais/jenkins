// seedMultibranchJob.groovy

def pipeline1 = [id: '123456789', displayName: 'dockerfile-repository', repositoryUrl: 'https://bitbucket.org/name-workspace/dockerfile-repository']

def pipelines = [pipeline1]
pipelines.each { pipeline ->
    println "Creating pipeline ${pipeline.displayName}"
    createMultibranchPipeline(pipeline)
}

def createMultibranchPipeline(pipeline) {
    multibranchPipelineJob(pipeline.displayName) {
        displayName(pipeline.displayName)
        branchSources {
            git {
                id(pipeline.id)
                remote(pipeline.repositoryUrl)
                credentialsId('bitbucket-credentials')
                includes('*')
            }
        }
        configure {
            def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
            traits << 'jenkins.plugins.git.traits.BranchDiscoveryTrait' {}
            // traits << 'jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait' {
            //     includes('*')
            // }
        }
        factory {
            workflowBranchProjectFactory {
                scriptPath('devops/Jenkinsfile')
            }
        }
        orphanedItemStrategy {
            discardOldItems {
                numToKeep(20)
            }
        }
    }
}
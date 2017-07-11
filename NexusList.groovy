@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1' )
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
def listArtifacts = []
def httpreq =  """ { "action": "coreui_Component",
    "method":"readAssets",
    "data":[{"page":"1", "start":"0",
    "limit":"300", "sort":[{"property":"name","direction":"ASC"}],
    "filter":[{"property":"repositoryName","value":"maven-releases"}]}],
    "type":"rpc",
    "tid":15
	} """

def remote = new HTTPBuilder("http://10.6.103.84:8081/")

remote.request(POST, TEXT) { req ->
    uri.path = "/service/extdirect"
    headers."Content-Type" = "application/json"
    headers.'Accept' = "*/*"
    //headers.Accept="application/json"
    body = httpreq

    headers.'Authorization' =
            "Basic ${"nexus-service-user:user".bytes.encodeBase64().toString()}"

    response.success = { resp, json ->
        println "DONE! ${resp.status}"

        def slurper = new groovy.json.JsonSlurper()
        def jsonRes = json.text as String
        def parsed = slurper.parseText(jsonRes)

        parsed.result.data.each {
            if (it.name.matches(~/.+.tar.gz/)){
                def myparser = it.name.substring(it.name.lastIndexOf("/")+1 , it.name.lastIndexOf("-")+3)
                listArtifacts.add(myparser)
            }
        }
    }
}
listArtifacts
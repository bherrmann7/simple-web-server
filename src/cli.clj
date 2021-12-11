
(ns cli)

(import org.docopt.Docopt)

(def usage (str
            "Simple Web Server\n"
            "\n"
            "Usage:\n"
            "  sws [options] \n"
            "  sws --help\n"
            "  sws --version\n"
            "\n"
            "Options:\n"
            "  -v --verbose  Verbose output.\n"
            "  -d --debug    Debug output.\n"
            "  -h --help     Show this screen.\n"
            "  --version     Show version.\n"
            "\n"))

(defn parse-cli [args]
  (into {} (.parse (.withVersion (Docopt. usage) "alpha") (into [] args))))



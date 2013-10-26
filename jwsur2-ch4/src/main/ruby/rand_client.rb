require "savon"

class RandClient

  def initialize(num_args)
    if num_args < 1
      raise RuntimeError, "Usage: " + __FILE__ + " <operation> [argument]"
    end
  
    wsdl_url = "http://localhost:8080/ch4/rand?wsdl"
    
    # namespaces = {
    #  "xmlns" => "http://rand.ch4.jwsur2.mkalin.github.com/"
    # }
    
    @client = Savon.client ({
      :wsdl => wsdl_url,
      :pretty_print_xml => true,
      :log => true,
      :log_level => :debug,
      #:namespaces => namespaces
      :element_form_default => :unqualified
    })     
  end
  
  def rand(operation, argument)
    response = invoke(operation, argument)
    
    response_tag = operation + "_response"
    process_response(response, response_tag)
  end
  
  private
  
  def invoke(operation, argument)
    message = { "arg0" => argument }
    
    @client.call(operation.to_sym, message: message)
  end
  
  def process_response(response, response_tag)
    puts response.body[response_tag.to_sym][:return]
  end
  
  num_args = ARGV.length
  
  rand_client = RandClient.new(num_args)
  
  operation = ARGV[0]
  
  argument = ""
  if (num_args >= 2)
    argument = ARGV[1]
  end
  
  rand_client.rand(operation, argument)
  
end

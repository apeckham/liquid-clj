require 'base64'

string = STDIN.read
string.gsub!(/\{([\{%])\s*?(.+?)\s*[\}%]\}/) do |matches|
%Q{<liquid type="#{$1 == "{" ? "string" : "code"}">#{Base64.encode64 $2.strip}</liquid>}
end
puts string

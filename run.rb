# run java tool, build class files if not exist
# Usage:
#  - # ruby run.rb [classname]

require 'find'
require 'fileutils'

$src_dir = 'src'
$lib_dir = 'lib'
$bin_dir = 'bin'

def get_file_list(dir, type)
    file_list = []
    Find.find(dir) { |file|
        file_list << file if file =~ /\.#{type}$/
    }
    return file_list
end

def exec_cmd(cmd)
    puts cmd
    system cmd
end

def main_class_name
    return File.basename(File.expand_path(File.dirname(__FILE__)))
    #return "main";
end

def main_class_file_exists?
    main_class_file = "#{$bin_dir}/main/#{main_class_name}.class"
    File.exists?(main_class_file)
end

$classpath_jar  = get_file_list($lib_dir, 'jar').join(':')
$classpath_src  = $src_dir
$classpath_bin  = $bin_dir

exec_cmd "rm -rf {$bin_dir}/*"

if !main_class_file_exists?
    $source_files = get_file_list($src_dir, 'java')
    FileUtils.mkpath $bin_dir
    exec_cmd "javac -encoding UTF-8 -d #{$bin_dir} -classpath #{$classpath_jar}:#{$classpath_src} #{$source_files.join(' ')}"

    #log4j_properties         = "#{$bin_dir}/log4j.properties"
    #log4j_properties_default = "#{$src_dir}/log4j.properties.default"
    #FileUtils.cp log4j_properties_default, log4j_properties if !File.exists? log4j_properties
end

#exec_cmd "java -classpath #{$classpath_jar}:#{$classpath_bin} main.main #{ARGV.join(' ')}"


#build a jar 
exec_cmd "jar -cvf ./FindFile.jar -C #{$bin_dir} ."

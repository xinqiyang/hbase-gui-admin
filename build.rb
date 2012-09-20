# run java tool, build class files if not exist
# Usage:
#  - # ruby run.rb [classname]

require 'find'
require 'fileutils'

$src_dir = 'src'
$lib_dir = 'lib'
$bin_dir = 'bin'
$tar_name = ARGV[0]
$tmp_dir = 'tmp'
$binlib_dir = 'bin/lib'


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
    main_class_file = "#{$tmp_dir}/#{main_class_name}.class"
    File.exists?(main_class_file)
end

exec_cmd "rm -rf ./#{$bin_dir}/*"
exec_cmd "rm -rf ./#{$tmp_dir}/*"


exec_cmd "mkdir -p ./#{$binlib_dir}/"
exec_cmd "mkdir -p ./#{$tmp_dir}/classes/"
exec_cmd "cp -r ./#{$lib_dir}/* ./#{$binlib_dir}/"

$classpath_jar  = get_file_list($binlib_dir, 'jar').join(':')
$classpath_src  = $src_dir
$classpath_bin  = $bin_dir

exec_cmd "cp -r src/META-INF ./tmp/classes/"
if !main_class_file_exists?
    $source_files = get_file_list($src_dir,'java')
    
    exec_cmd "javac -encoding UTF-8 -d #{$tmp_dir}/classes -classpath #{$classpath_jar} #{$source_files.join(' ')}"

end

#exec_cmd "java -classpath #{$classpath_jar}:#{$classpath_bin} main.main #{ARGV.join(' ')}"


#build a jar 
#exec_cmd "jar -cvf ./#{$bin_dir}/#{$tar_name}.jar -C ./build/classes ."
#exec_cmd "jar -mcf MANIFEST.MF ./bin/TestS.jar  -C ./#{$tmp_dir}/classes ."
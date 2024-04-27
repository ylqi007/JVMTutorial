#!/bin/zsh
set -x

function fix_content_root() {
    local in_directory="$1"
    local targets=($(grep -E -l --include \*.iml '<content url="file://\$MODULE_DIR\$/(\.\./)+src/main/resources"' -r ${in_directory} | grep -v 'fixContentRoot.sh'))
    for target in ${targets[@]}; do
        echo ${target};
        sed -Ei 's#<content url="file://\$MODULE_DIR\$/(\.\./)+src/main/resources" />##g' ${target};
        sed -i '/<content url="file:\/\/$MODULE_DIR$\/..\/src\/main\/resources">/{:a;N;/<\/content>/!ba};/<sourceFolder url="file:\/\/$MODULE_DIR$\/..\/src\/main\/resources" type="java-resource" \/>/d' ${target};
        sed -i '/<content url="file:\/\/$MODULE_DIR$\/..\/..\/src\/main\/resources">/{:a;N;/<\/content>/!ba};/<sourceFolder url="file:\/\/$MODULE_DIR$\/..\/..\/src\/main\/resources" type="java-resource" \/>/d' ${target};
        sed -i '/<content url="file:\/\/$MODULE_DIR$\/..\/..\/..\/src\/main\/resources">/{:a;N;/<\/content>/!ba};/<sourceFolder url="file:\/\/$MODULE_DIR$\/..\/..\/..\/src\/main\/resources" type="java-resource" \/>/d' ${target};
    done
}

fix_content_root "${HOME}/Work/IDEA/JVMTutorial"
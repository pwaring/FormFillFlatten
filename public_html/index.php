<?php

define('JAVA_PATH', '/usr/bin/java');
define('TMP_DIR', '/tmp');
define('FFF_PATH', dirname(dirname(__FILE__)));
define('CLASSPATH', FFF_PATH . '/itext.jar:' . FFF_PATH . '/json-org.jar:' . FFF_PATH . '/');

$template_tmp = isset($_FILES['template']['tmp_name']) ? $_FILES['template']['tmp_name'] : '';
$config_tmp = isset($_FILES['config']['tmp_name']) ? $_FILES['config']['tmp_name'] : '';

$template = tempnam(TMP_DIR, 'FFF');
$config = tempnam(TMP_DIR, 'FFF');
$output = tempnam(TMP_DIR, 'FFF');

// Move uploaded files, otherwise they may be deleted before we call FFF
move_uploaded_file($template_tmp, $template);
move_uploaded_file($config_tmp, $config);

$execstr = JAVA_PATH . ' -classpath ' . CLASSPATH . " FormFillFlatten $config $template $output";

exec($execstr);

echo file_get_contents($output);

?>

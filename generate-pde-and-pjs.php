<?php

/**
 * Generate Game/Tetris.pde and Game/Tetris.pjs from all the java source files from Game/src folder.
 * Tetris.pde can be run in Processing (applet version of the game).
 * Tetris.pjs can be run in Processing.js (javascript version of the game).
 */

ob_start();
// Check ip.
$ip = $_SERVER['REMOTE_ADDR'];
if (!in_array($ip, array('127.0.0.1'))) {
    return;
}

// Constants.
include 'define.php';
defined('DS') || define('DS', '\\');
defined('APP_PATH') || define('APP_PATH', realpath(dirname(__FILE__)) . DS);

// Parameters.
$fromDir     = APP_PATH . 'Game' . DS . 'src';
$pdeFolder   = APP_PATH . 'Game-pde' . DS . 'Tetris' . DS;
$pdeFilename = $pdeFolder . 'Tetris.pde';
$pjsFolder   = APP_PATH . 'Game-pjs' . DS;
$pjsFilename = $pjsFolder . 'Tetris.pjs';

// Exclude folder/files:
$excludeFolders = array(
    $fromDir . DS . 'Genetic',
);

// Content to remove.
$excludeContentOptions = array(
    /***********
     * BOTH
     **********/
    // Delete applet class 'extends PApplet'. Processing and Processing.js don't know about PApplet.
    array(
        'content' => 'extends PApplet',
    ),
    // Delete unnecessary import from pde file.
    array(
        'content'      => 'import processing.core.PApplet;',
        'onlyForFiles' => array(DS . 'Tetris' . DS . 'Tetris.java'),
    ),
    // Delete @Override annotations. Processing.js doesn't allow them. Processing doesn't like them.
    array(
        'content' => <<<JAVA
    @Override

JAVA
    ),

    /***********
     * PDE
     **********/
    // Remove any way the PApplet is accessed, in processing the PApplet functions are recognised anywhere in the code.
    array(
        'content' => 'this.app.',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => 'this.getParentController().getView().getParentView().getApp().',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => 'this.getParentController().getView().getApp().',
        'onlyFor' => 'pjs'),
    array(
        'content' => 'this.getView().getParentView().getParentView().getApp().',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => 'this.getView().getParentView().getApp().',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => 'this.getParentView().getParentView().getApp().',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => 'this.getParentView().getApp().',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => 'PApplet.',
        'onlyFor' => 'pjs',
    ),
    //For some reason a constructor throwing exception gives error
    // "Unable to execute pjs sketch: TypeError: Cannot read property '1' of null" on processing.js compilation.
    array(
        'content' => 'throws Model_Exception',
        'onlyFor' => 'pjs',
    ),
    array(
        'content' => '@removeLineFromPde',
        'onlyFor' => 'pjs',
    ),

    /***********
     * PJS
     **********/
    array(
        'content' => '@removeLineFromPjs',
        'onlyFor' => 'pde',
    ),
);

// Lines to remove.
$excludeLineContentOptions = array(
    /***********
     * BOTH
     **********/
    // Remove liens containing the @removeLineFromAll text.
    array('pattern' => '/@removeLineFromAll/'),
    // Delete lines starting with package.
    array('pattern' => '/^package/'),

    /***********
     * PDE
     **********/
    // Remove lines containing the @removeLineFromPde text.
    array('pattern' => '/@removeLineFromPde/',

          'onlyFor' => 'pde'),
    // Processing doesn't allow packages.
    array('pattern' => '/^import Tetris/',
          'onlyFor' => 'pde'),

    /***********
     * PJS
     **********/
    // Remove lines containing the @removeLineFromPjs text.
    array('pattern' => '/@removeLineFromPjs/',
          'onlyFor' => 'pjs'),

    // Delete lines starting with import. Processing.js ignores these lines.
    array('pattern' => '/^import/',
          'onlyFor' => 'pjs'),

);

// Get all files from fromDir.
$checkOnlyJava = function($dir, $file) {
    return substr($file, -4) == 'java';
};
// Exclude folders.
$checkExcludeFolders = function ($dir, $sub) {
    global $excludeFolders;
    $ok = !in_array($dir . DS . $sub, $excludeFolders);
    if ($ok) {
        echo $dir . DS . $sub . "<br>\n";
    }
    return $ok;
};
// Get files.
$files = dirToArray($fromDir, $checkOnlyJava, $checkExcludeFolders);

// Function to get all files from directory $dir and all its subdirectories.
function dirToArray($dir, $checkFile = null, $checkDir = null) {
    $result = array();
    $files  = scandir($dir);

    foreach ($files as $key => $value) {
        if (!in_array($value, array(".", ".."))) {
            if (is_dir($dir . DS . $value)
            ) {
                if (!$checkDir || $checkDir($dir, $value)) {
                    $result[$value] = dirToArray($dir . DS . $value, $checkFile, $checkDir);
                }
            }
            else {
                if (!$checkFile || $checkFile($dir, $value)) {
                    $result[] = $value;
                }
            }
        }
    }

    return $result;
}

// Create Game/Tetris.pde and Game/Tetris.pjs.
$pdeFile = fopen($pdeFilename, 'w');
if ($pdeFile === false) {
    die();
}
$pjsFile = fopen($pjsFilename, 'w');
if ($pjsFile === false) {
    die();
}

// Go through all files and include them in the pde file.
checkFiles('', $files);

// Function to recursively include all files from array of files $files, which are in directory $dir.
function checkFiles($dir, $files) {
    // First check dirs.
    foreach ($files as $key => $value) {
        if (is_array($value)) {
            checkFiles($dir . DS . $key, $value);
        }
    }

    // Then check files.
    foreach ($files as $key => $value) {
        if (!is_array($value)) {
            global $pdeFile, $pjsFile;
            checkFile($dir, $value, $pdeFile, 'pde');
            checkFile($dir, $value, $pjsFile, 'pjs');
        }
    }
}

// Function to get the contents of $file from $dir, filter it and include it in the $toFile of type $typeToFile.
function checkFile($dir, $file, $toFile, $typeToFile) {
    // Get file content.
    global $fromDir;
    $content = file_get_contents($fromDir . DS . $dir . DS . $file);

    // Exclude content.
    global $excludeContentOptions;
    foreach ($excludeContentOptions as $excludeContentOption) {
        if (optionIsValidForFile($excludeContentOption, $dir . DS . $file, $typeToFile)) {
            $content = str_replace($excludeContentOption['content'], '', $content);
        }
    }

    // Make the remained content an array of lines.
    $lines = explode("\n", $content);
    // Prepare final lines.
    $finalLines = array();

    // Go through all lines.
    global $excludeLineContentOptions;
    foreach ($lines as $line) {
        $ok = true;
        // If it matches on of the excludeLineContentOptions patterns, then ok = false.
        foreach ($excludeLineContentOptions as $excludeLineContentOption) {
            $ok = !optionIsValidForFile($excludeLineContentOption, $dir . DS . $file, $typeToFile)
                || preg_match($excludeLineContentOption['pattern'], $line) === 0;
            if (!$ok) {
                break;
            }
        }
        // If ok remained true, then add it in the final lines.
        if ($ok) {
            $finalLines[] = $line;
        }
    }

    // Prepare final content.
    $finalContent = implode("\n", $finalLines);

    if ($typeToFile == 'pjs') {
        // For pjs , include file in the pjs file.
        fwrite($toFile, $finalContent);
    } else {
        // For pde, create a new file.
        if ($file == "StartGame.java") {
            $file = "Tetris.pde";
        }
        global $pdeFolder;
        $file = fopen($pdeFolder . $file, 'w');
        if ($file === false) {
            die();
        }
        fwrite($file, $finalContent);
        fclose($file);
    }
}

/**
 * Checks if option is valid for the file, which is included in the content of type contentType (pde or pjs).
 * @param $option
 * @param $file
 * @param $contentType
 * @return bool
 */
function optionIsValidForFile($option, $file, $contentType) {
    // Option is given only for some files.
    $onlyForFiles = isset($option['onlyForFiles']) ? $option['onlyForFiles'] : null;
    if ($onlyForFiles && !in_array($file, $onlyForFiles)) {
        return false;
    }

    // Option is given only for pde or pjs.
    $onlyFor = isset($option['onlyFor']) ? $option['onlyFor'] : null;
    if ($onlyFor && $onlyFor != $contentType) {
        return false;
    }

    // It's good.
    return true;
}

// Close file.
fclose($pdeFile);

$content = ob_get_clean();
echo "OK<br>\n";
echo $content;
@rem Schema Factoring tool
@rem
@rem Factores redundant definitions out of a set of schemas and uses imports instead.

@echo off

setlocal
if "%XMLBEANS_HOME%" EQU "" (set XMLBEANS_HOME=%~dp0..)

set cp=

if EXIST %XMLBEANS_HOME%\build\ar\xbean.jar. (
    set cp=%XMLBEANS_HOME%\build\ar\xbean.jar;%XMLBEANS_HOME%\build\lib\xml-apis.jar;%XMLBEANS_HOME%\build\lib\xercesImpl.jar.
) else if EXIST %XMLBEANS_HOME%\lib\xbean.jar. (
    set cp=%XMLBEANS_HOME%\build\lib\xbean.jar;%XMLBEANS_HOME%\lib\xml-apis.jar;%XMLBEANS_HOME%\lib\xercesImpl.jar.
)

java -classpath %cp% org.apache.xmlbeans.impl.tool.FactorImports %*

:done

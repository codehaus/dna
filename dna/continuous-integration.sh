#!/bin/bash

#JAVA_HOME=/usr/local/j2sdk1.4.1
#MAVEN_HOME=~bwalding/maven
#ANT_HOME=$HOME/cvs/ant/dist
PATH=$HOME/bin:$JAVA_HOME/bin:$MAVEN_HOME/bin:$ANT_HOME/bin:$PATH
#CVS_RSH=$HOME/ssh1.sh
#CVSROOT=:ext:$USER@cvs.codehaus.org:/cvsroot/picocontainer

export JAVA_HOME
export MAVEN_HOME
export ANT_HOME
export PATH
export CVS_RSH
export CVSROOT

#mailto=osi@pobox.com
mailto=dev@dna.codehaus.org
builddir=.
logfile=cleanbuild.log
name=DNA
mavenRepo=dna

cd $builddir

# Delete compiled local copies to start fresh each time
rm -Rf ~/.maven/repository/$mavenRepo/jars

# Compile and test
rm -f $logfile
maven 2>&1 | tee $logfile

if grep "BUILD SUCCESSFUL" $logfile ; then
      echo "$name build successful"
else
      echo "$name clean failed, emailing list"
      cat $logfile | mail -s "[FAIL] $name compilation." $mailto
fi

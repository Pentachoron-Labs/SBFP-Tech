#!/bin/bash
sh ./recompile.sh
sh ./reobfuscate_srg.sh
cp -urv reobf/minecraft/sbfp .
cp -urv src/minecraft/mods .
cp -uv src/minecraft/mcmod.info .
jar cf sbfptech-$1.$(git rev-list HEAD --count).jar sbfp mods mcmod.info
#Clean up
rm -rv sbfp mods mcmod.info

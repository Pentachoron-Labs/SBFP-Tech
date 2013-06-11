#!/bin/bash
sh ./recompile.sh
sh ./reobfuscate.sh
cp -urv reobf/minecraft/sbfp .
cp -urv src/minecraft/mods .
jar cf sbfptech-$1.$(git rev-list HEAD --count).jar sbfp mods mcmod.info
function fileConf(fileName) {
  return {
    input: `dist/esm/${fileName}.js`,
    output: [
      {
        file: `dist/${fileName}.js`,
        format: 'iife',
        name: 'capacitorFirebaseFirestore',
        globals: {
          '@capacitor/core': 'capacitorExports',
          'firebase/firestore': 'firebaseFirestoreExports',
        },
        sourcemap: true,
        inlineDynamicImports: true,
      },
      {
        file: `dist/${fileName}.cjs.js`,
        format: 'cjs',
        sourcemap: true,
        inlineDynamicImports: true,
      },
    ],
    external: ['@capacitor/core'],
  };
}

export default [
  fileConf('index'),
  fileConf('field-value'),
  fileConf('timestamp'),
];

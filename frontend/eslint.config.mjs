import globals from "globals";
import pluginJs from "@eslint/js";
import pluginReact from "eslint-plugin-react";


/** @type {import('eslint').Linter.Config[]} */
export default [
  {files: ["**/*.{js,mjs,cjs,jsx}"]},
  {files: ["**/*.js"], languageOptions: {sourceType: "commonjs"}},
  {languageOptions: { globals: globals.browser }},
  pluginJs.configs.recommended,
  pluginReact.configs.flat.recommended,
  {
    extends: [
      "airbnb",
      "eslint:recommended",
      "plugin:prettier/recommended"
    ],
    plugins: [
      "prettier"
    ],
    rules: {
      "prettier/prettier": "error"
    },
    settings: {
      react: {
        version: "detect",
      },
    }
  }
];
const path = require('path');

module.exports = [
  {
    entry: './src/login.js',
    output: {
      path: path.join(__dirname, '../oxygen-account/src/main/resources/static/app'),
      filename: 'login.js',
    },
    mode: "development",
    module: {
      rules: [
        {
          test: /\.(js|jsx)$/,
          exclude: /node_modules/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env', '@babel/preset-react'],
            },
          },
        },
        {
          test: /\.css$/,
          use: ['style-loader', 'css-loader'],
        },
      ],
    },
  },
  
  {
    entry: './src/profile.js',
    output: {
      path: path.join(__dirname, '../oxygen-account/src/main/resources/static/app'),
      filename: 'profile.js',
    },
    mode: "development",
    module: {
      rules: [
        {
          test: /\.(js|jsx)$/,
          exclude: /node_modules/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env', '@babel/preset-react'],
            },
          },
        },
        {
          test: /\.css$/,
          use: ['style-loader', 'css-loader'],
        },
      ],
    },
  }
];
// jest.config.mjs
export default {
  testEnvironment: 'node',
  transform: {
    "^.+\\.js$": "babel-jest"
  },
  testPathIgnorePatterns: ["/node_modules/", "/tests/e2e/"]
};

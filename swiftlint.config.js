const base = require('@ionic/swiftlint-config');

module.exports = {
  ...base,
  excluded: [
    ...(base.excluded || []).map((p) => p.replace('${PWD}', process.cwd())),
    `${process.cwd()}/.build`,
    `${process.cwd()}/example`,
  ],
};

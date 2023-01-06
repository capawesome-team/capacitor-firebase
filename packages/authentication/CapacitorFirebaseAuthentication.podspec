require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapacitorFirebaseAuthentication'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target  = '13.0'
  s.dependency 'Capacitor'
  s.dependency 'Firebase/Auth', '10.2.0'
  s.swift_version = '5.1'
  s.static_framework = true
  s.default_subspec = 'Lite'

  s.subspec 'Lite' do |lite|
    # Default subspec that does not contain optional third party dependencies.
  end

  s.subspec 'Google' do |google|
    google.xcconfig = { 'OTHER_SWIFT_FLAGS' => '$(inherited) -DRGCFA_INCLUDE_GOOGLE' }
    google.dependency 'GoogleSignIn', '6.2.4'
  end

  s.subspec 'Facebook' do |facebook|
    facebook.xcconfig = { 'OTHER_SWIFT_FLAGS' => '$(inherited) -DRGCFA_INCLUDE_FACEBOOK' }
    facebook.dependency 'FBSDKCoreKit', '15.1.0'
    facebook.dependency 'FBSDKLoginKit', '15.1.0'
  end
end

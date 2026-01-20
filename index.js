import { NativeModules, Platform } from 'react-native';

const { BroadcastModule, BroadcastModuleV2 } = NativeModules;

export function sendBroadcast(action) {
  if (Platform.OS === 'android') {
    BroadcastModule.sendBroadcast(action);
  }
}

export function sendMessage(action) {
  if (Platform.OS === 'android') {
    BroadcastModuleV2.sendMessage(action);
  }
}

export function openCamera() {
  if (Platform.OS === 'android') {
    BroadcastModule.openCamera();
  }
}
import { NativeModules, Platform } from 'react-native';

const { BroadcastModule } = NativeModules;

export function sendBroadcast(action) {
  if (Platform.OS === 'android') {
    BroadcastModule.sendBroadcast(action);
  }
}

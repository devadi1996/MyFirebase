import React, { Component } from 'react'
import { Text, View, StyleSheet, SafeAreaView, TouchableOpacity, NativeModules, Platform, Alert, TextInput } from 'react-native'
import ImagePicker from 'react-native-image-crop-picker';
import Clipboard from "@react-native-community/clipboard";
const { TextRecognition } = NativeModules;
export class App extends Component {
  state = {
    text: ''
  };


  componentDidMount() {
    NativeModules.TextRecognition.increment();
    NativeModules.TextRecognition.getCount((value: any) => {
      console.log("count is " + value)
      this.decrement();
      this.decrement();

    })
  }


  decrement() {
    NativeModules.TextRecognition.decrement()
      .then((res: any) => console.log(res))
      .catch((e: any) => console.log(e.message, e.code))
  }

  render() {
    return (
      <SafeAreaView style={styles.contaier}>
        {/* <TouchableOpacity style={styles.button} onPress={() => {
          TextRecognition.showText('Native Toast', TextRecognition.LENGTH_LONG)

        }}>

          <Text>Toast</Text>
        </TouchableOpacity> */}
        <TouchableOpacity style={styles.button} onPress={() => {

          ImagePicker.openPicker({
            cropping: true
          }).then((image: any) => {
            let path = Platform.OS == 'ios' ? "file:///" + image.path : image.path;
            TextRecognition.imageFromPath(path, (text: any) => {
              this.setState({
                text
              })
            })
          });
        }}>

          <Text>scan</Text>
        </TouchableOpacity>
        <TextInput
          value={this.state.text}
          onChangeText={(text) => {
            this.setState({ text })
          }}
          multiline={true}
          style={{
            width: 300,

          }}
        />
      </SafeAreaView>
    )
  }
}
const styles = StyleSheet.create({
  contaier: {
    flex: 1
  },
  button: {
    width: 100,
    height: 60,
    backgroundColor: 'pink',
    justifyContent: 'center',
    alignItems: 'center'
  }
})

export default App

/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.fuzz.input;

import tech.pegasys.teku.spec.Spec;
import tech.pegasys.teku.spec.SpecVersion;
import tech.pegasys.teku.spec.datastructures.blocks.BeaconBlock;
import tech.pegasys.teku.spec.datastructures.state.beaconstate.BeaconState;
import tech.pegasys.teku.ssz.backing.containers.Container2;
import tech.pegasys.teku.ssz.backing.containers.ContainerSchema2;
import tech.pegasys.teku.ssz.backing.tree.TreeNode;

/**
 * Note: BlockHeader fuzzing target accepts a block as input (not a SignedBeaconBlock or
 * BeaconBlockHeader)
 */
public class BlockHeaderFuzzInput
    extends Container2<BlockHeaderFuzzInput, BeaconState, BeaconBlock> {

  public static ContainerSchema2<BlockHeaderFuzzInput, BeaconState, BeaconBlock> createType(
      final SpecVersion spec) {
    return ContainerSchema2.create(
        spec.getSchemaDefinitions().getBeaconStateSchema(),
        BeaconBlock.SSZ_SCHEMA.get(),
        BlockHeaderFuzzInput::new);
  }

  private BlockHeaderFuzzInput(
      ContainerSchema2<BlockHeaderFuzzInput, BeaconState, BeaconBlock> type, TreeNode backingNode) {
    super(type, backingNode);
  }

  public BlockHeaderFuzzInput(final Spec spec, final BeaconState state, final BeaconBlock block) {
    super(createType(spec.atSlot(state.getSlot())), state, block);
  }

  public BeaconBlock getBlock() {
    return getField1();
  }

  public BeaconState getState() {
    return getField0();
  }
}

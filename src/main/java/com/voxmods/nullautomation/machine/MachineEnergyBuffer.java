/**
 * This file is part of Null Automation.
 * Copyright (c) 2016 Benjamin Ward, All rights reserved.
 * <p>
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 * <p>
 * Created by Vox (Benjamin Ward) on 5/8/2016.
 */
package com.voxmods.nullautomation.machine;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class MachineEnergyBuffer implements ITeslaConsumer, ITeslaProducer, ITeslaHolder, INBTSerializable<NBTTagCompound> {

    /**
     * The amount of stored Tesla power.
     */
    protected long stored;
    /**
     * The maximum amount of Tesla power that can be stored.
     */
    protected long capacity;
    /**
     * The maximum amount of Tesla power that can be accepted.
     */
    protected long inputRate;
    /**
     * The maximum amount of Tesla power that can be extracted.
     */
    protected long outputRate;

    /**
     * Constructor for setting the basic values. Will not construct with any stored power.
     *
     * @param capacity The maximum amount of Tesla power that the container should hold.
     * @param maxTransferRate maximum rate of power that can be accepted/extracted at a time.
     */
    public MachineEnergyBuffer(int capacity, int maxTransferRate) {

        this(capacity, maxTransferRate, maxTransferRate);
    }

    /**
     * Constructor for setting the basic values. Will not construct with any stored power.
     *
     * @param capacity The maximum amount of Tesla power that the container should hold.
     * @param input The maximum rate of power that can be accepted at a time.
     * @param output The maximum rate of power that can be extracted at a time.
     */
    public MachineEnergyBuffer(long capacity, long input, long output) {

        this.capacity = capacity;
        this.inputRate = input;
        this.outputRate = output;
    }

    /**
     * Constructor for setting all of the base values, including the stored power.
     *
     * @param power The amount of stored power to initialize the container with.
     * @param capacity The maximum amount of Tesla power that the container should hold.
     * @param input The maximum rate of power that can be accepted at a time.
     * @param output The maximum rate of power that can be extracted at a time.
     */
    public MachineEnergyBuffer(long power, long capacity, long input, long output) {

        this.stored = power;
        this.capacity = capacity;
        this.inputRate = input;
        this.outputRate = output;
    }

    /**
     * Constructor for creating an instance directly from a compound tag. This expects that the
     * compound tag has some of the required data. @See {@link #deserializeNBT(NBTTagCompound)}
     * for precise info on what is expected. This constructor will only set the stored power if
     * it has been written on the compound tag.
     *
     * @param dataTag The NBTCompoundTag to read the important data from.
     */
    public MachineEnergyBuffer(NBTTagCompound dataTag) {

        this.deserializeNBT(dataTag);
    }

    @Override
    public NBTTagCompound serializeNBT () {

        final NBTTagCompound dataTag = new NBTTagCompound();
        dataTag.setLong("TeslaPower", this.stored);
        dataTag.setLong("TeslaCapacity", this.capacity);
        dataTag.setLong("TeslaInput", this.inputRate);
        dataTag.setLong("TeslaOutput", this.outputRate);

        return dataTag;
    }

    @Override
    public void deserializeNBT (NBTTagCompound nbt) {

        this.stored = nbt.getLong("TeslaPower");

        if (nbt.hasKey("TeslaCapacity"))
            this.capacity = nbt.getLong("TeslaCapacity");

        if (nbt.hasKey("TeslaInput"))
            this.inputRate = nbt.getLong("TeslaInput");

        if (nbt.hasKey("TeslaOutput"))
            this.outputRate = nbt.getLong("TeslaOutput");

        if (this.stored > this.getCapacity())
            this.stored = this.getCapacity();
    }

    @Override
    public long getStoredPower () {

        return this.stored;
    }

    @Override
    public long givePower (long Tesla, boolean simulated) {

        final long acceptedTesla = Math.min(this.getCapacity() - this.stored, Math.min(inputRate, Tesla));

        if (!simulated)
            this.stored += acceptedTesla;

        return acceptedTesla;
    }

    @Override
    public long takePower (long Tesla, boolean simulated) {

        final long removedPower = Math.min(this.stored, Math.min(outputRate, Tesla));

        if (!simulated)
            this.stored -= removedPower;

        return removedPower;
    }

    @Override
    public long getCapacity () {

        return this.capacity;
    }
}
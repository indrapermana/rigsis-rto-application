import { Moment } from 'moment';
import { IWell } from 'app/shared/model//well.model';
import { IWellbore } from 'app/shared/model//wellbore.model';
import { IRig } from 'app/shared/model//rig.model';

export const enum Status {
    Active = 'Active',
    Inactive = 'Inactive'
}

export const enum BooleanType {
    Yes = 'Yes',
    No = 'No'
}

export interface IWellbore {
    id?: number;
    name?: string;
    wellName?: string;
    parentWellboreName?: string;
    govermentNumber?: string;
    status?: Status;
    purpose?: string;
    type?: string;
    shape?: string;
    dayTarget?: number;
    kickOffDate?: Moment;
    achievedTD?: BooleanType;
    mdCurrent?: number;
    tvdCurrent?: number;
    mdBitCurrent?: number;
    tvdBitCurrent?: number;
    mdKickOff?: number;
    tvdKickOff?: number;
    mdPlanned?: number;
    tvdPlanned?: number;
    mdSubSea?: number;
    tvdSubSea?: number;
    well?: IWell;
    parent?: IWellbore;
    rigs?: IRig[];
}

export class Wellbore implements IWellbore {
    constructor(
        public id?: number,
        public name?: string,
        public wellName?: string,
        public parentWellboreName?: string,
        public govermentNumber?: string,
        public status?: Status,
        public purpose?: string,
        public type?: string,
        public shape?: string,
        public dayTarget?: number,
        public kickOffDate?: Moment,
        public achievedTD?: BooleanType,
        public mdCurrent?: number,
        public tvdCurrent?: number,
        public mdBitCurrent?: number,
        public tvdBitCurrent?: number,
        public mdKickOff?: number,
        public tvdKickOff?: number,
        public mdPlanned?: number,
        public tvdPlanned?: number,
        public mdSubSea?: number,
        public tvdSubSea?: number,
        public well?: IWell,
        public parent?: IWellbore,
        public rigs?: IRig[]
    ) {}
}

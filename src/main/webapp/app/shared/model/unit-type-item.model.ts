import { IUnitType } from 'app/shared/model//unit-type.model';

export interface IUnitTypeItem {
    id?: number;
    name?: string;
    conversionType?: string;
    symbol?: string;
    factor?: number;
    unitType?: IUnitType;
}

export class UnitTypeItem implements IUnitTypeItem {
    constructor(
        public id?: number,
        public name?: string,
        public conversionType?: string,
        public symbol?: string,
        public factor?: number,
        public unitType?: IUnitType
    ) {}
}

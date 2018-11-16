import { IBaseUnit } from 'app/shared/model//base-unit.model';
import { IUnitTypeItem } from 'app/shared/model//unit-type-item.model';

export interface IUnitType {
    id?: number;
    type?: string;
    baseUnit?: IBaseUnit;
    unitTypeItems?: IUnitTypeItem[];
}

export class UnitType implements IUnitType {
    constructor(public id?: number, public type?: string, public baseUnit?: IBaseUnit, public unitTypeItems?: IUnitTypeItem[]) {}
}

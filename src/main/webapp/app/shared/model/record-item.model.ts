import { IRecordType } from 'app/shared/model//record-type.model';

export interface IRecordItem {
    id?: number;
    number?: number;
    name?: string;
    mnemonic?: string;
    specialCase?: string;
    unitType?: string;
    unit?: string;
    dataType?: string;
    nullValue?: number;
    description?: string;
    recordType?: IRecordType;
}

export class RecordItem implements IRecordItem {
    constructor(
        public id?: number,
        public number?: number,
        public name?: string,
        public mnemonic?: string,
        public specialCase?: string,
        public unitType?: string,
        public unit?: string,
        public dataType?: string,
        public nullValue?: number,
        public description?: string,
        public recordType?: IRecordType
    ) {}
}

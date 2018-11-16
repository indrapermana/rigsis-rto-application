/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { UnitTypeItemDetailComponent } from 'app/entities/unit-type-item/unit-type-item-detail.component';
import { UnitTypeItem } from 'app/shared/model/unit-type-item.model';

describe('Component Tests', () => {
    describe('UnitTypeItem Management Detail Component', () => {
        let comp: UnitTypeItemDetailComponent;
        let fixture: ComponentFixture<UnitTypeItemDetailComponent>;
        const route = ({ data: of({ unitTypeItem: new UnitTypeItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [UnitTypeItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UnitTypeItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UnitTypeItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.unitTypeItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

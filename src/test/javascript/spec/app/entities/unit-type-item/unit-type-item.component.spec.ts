/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { UnitTypeItemComponent } from 'app/entities/unit-type-item/unit-type-item.component';
import { UnitTypeItemService } from 'app/entities/unit-type-item/unit-type-item.service';
import { UnitTypeItem } from 'app/shared/model/unit-type-item.model';

describe('Component Tests', () => {
    describe('UnitTypeItem Management Component', () => {
        let comp: UnitTypeItemComponent;
        let fixture: ComponentFixture<UnitTypeItemComponent>;
        let service: UnitTypeItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [UnitTypeItemComponent],
                providers: []
            })
                .overrideTemplate(UnitTypeItemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnitTypeItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitTypeItemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UnitTypeItem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.unitTypeItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

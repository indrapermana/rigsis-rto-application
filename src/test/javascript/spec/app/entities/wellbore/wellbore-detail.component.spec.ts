/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { WellboreDetailComponent } from 'app/entities/wellbore/wellbore-detail.component';
import { Wellbore } from 'app/shared/model/wellbore.model';

describe('Component Tests', () => {
    describe('Wellbore Management Detail Component', () => {
        let comp: WellboreDetailComponent;
        let fixture: ComponentFixture<WellboreDetailComponent>;
        const route = ({ data: of({ wellbore: new Wellbore(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WellboreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WellboreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WellboreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wellbore).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
